package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.OrdemDtoRequest;
import br.com.lex4crypto.monolito.dtos.OrdemDtoResponse;
import br.com.lex4crypto.monolito.enums.CryptoMoeda;
import br.com.lex4crypto.monolito.enums.TipoOrdem;
import br.com.lex4crypto.monolito.exception.CarteiraNotFoundException;
import br.com.lex4crypto.monolito.exception.SaldoInsuficienteException;
import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.models.Cliente;
import br.com.lex4crypto.monolito.models.interfaces.Ordem;
import br.com.lex4crypto.monolito.repositories.CorretoraRepository;
import br.com.lex4crypto.monolito.service.livros.LivroBitcoinService;
import br.com.lex4crypto.monolito.service.livros.LivroCardanoService;
import br.com.lex4crypto.monolito.service.livros.LivroEthereumService;
import br.com.lex4crypto.monolito.service.livros.LivroSolanaService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CorretoraService {

    private final CorretoraRepository corretoraRepository;
    private final ClienteService clienteService;
    private final VendaService vendaService;

    private final LivroBitcoinService livroBitcoinService;
    private final LivroEthereumService livroEthereumService;
    private final LivroCardanoService livroCardanoService;
    private final LivroSolanaService livroSolanaService;
    private static final BigDecimal TAXA_CORRETAGEM = BigDecimal.valueOf(0.005); //Pode ser substituído pelo consumo de uma API

    public CorretoraService(CorretoraRepository corretoraRepository, ClienteService clienteService, VendaService vendaService, LivroBitcoinService livroBitcoinService, LivroEthereumService livroEthereumService, LivroCardanoService livroCardanoService, LivroSolanaService livroSolanaService) {
        this.corretoraRepository = corretoraRepository;
        this.clienteService = clienteService;
        this.vendaService = vendaService;
        this.livroBitcoinService = livroBitcoinService;
        this.livroEthereumService = livroEthereumService;
        this.livroCardanoService = livroCardanoService;
        this.livroSolanaService = livroSolanaService;
    }

    public OrdemDtoResponse lancarVenda(OrdemDtoRequest ordemDtoRequest){
        //obter dados usuario
        Cliente cliente = clienteService.findClienteByUsername(ordemDtoRequest.getUsernameCliente());

        //validar pedido de venda
        Carteira carteiraCrypto = recuperarCarteiraCrypto(cliente, ordemDtoRequest.getCryptoMoeda());
        BigDecimal valorTotal = ordemDtoRequest.getValorUnitario().multiply(ordemDtoRequest.getQuantidade());
        if (!verificarSaldo(carteiraCrypto,valorTotal)){
            throw new SaldoInsuficienteException("Saldo insuficiente. Saldo do cliente: " +
                    carteiraCrypto.getQuantidade() + " " + carteiraCrypto.getCryptoMoeda().toString());
        }

        //criar ordem de venda
        Ordem ordemVenda = new Ordem();
        BeanUtils.copyProperties(ordemDtoRequest, ordemVenda);
        ordemVenda.setTipoOrdem(TipoOrdem.VENDA);
        calcularValorOrdem(ordemVenda);
        ordemVenda.setValorTaxaCorretagem(calcularTaxaCorretagem(ordemVenda));
        ordemVenda.setValorTotal(calcularValorTotalOrdem(ordemVenda));

        //salvar pedido de venda
        Ordem ordemSalva = vendaService.save(ordemVenda);
        //salvar pedido de venda no livro
        registrarVendaNoLivro(ordemSalva, ordemSalva.getCryptoMoeda());

        //retorna DTO ordem de venda criada
        OrdemDtoResponse response = new OrdemDtoResponse();
        BeanUtils.copyProperties(ordemSalva, response);
        return response;
    }

//    public OrdemDtoResponse processarOrdem(OrdemDtoRequest ordemDtoRequest) {
//
//        //Recuperar cliente
//        Cliente cliente = clienteService.findClienteByUsername(ordemDtoRequest.getUsernameCliente());
//
//        //Atribuir tipo da ordem
//        Ordem ordem = verificarTipoOrdem(ordemDtoRequest);
//
//        //Atribuir valor total da corretagem
//        ordem.setValorTaxaCorretagem(calcularTaxaCorretagem(ordem));
//
//        //Atribuir valor total da ordem
//        ordem.setValorTotal(calcularValorTotalOrdem(ordem));
//
//        //Executar a ordem
//        if (aprovarSaldo(ordem,cliente)){
//            ordem.executar(cliente);
//        }else {
//            throw new SaldoInsuficienteException("Saldo insuficiente");
//        }
//
//        return null;
//    }

//    public Ordem verificarTipoOrdem(OrdemDtoRequest ordemDtoRequest){
//        if (ordemDtoRequest.getTipoOrdem().equals(TipoOrdem.VENDA)){
//            OrdemVenda ordemVenda = new OrdemVenda();
//            BeanUtils.copyProperties(ordemDtoRequest, ordemVenda);
//            return ordemVenda;
//        }else {
//            OrdemCompra ordemCompra = new OrdemCompra();
//            BeanUtils.copyProperties(ordemDtoRequest, ordemCompra);
//            return ordemCompra;
//        }
//    }

    private Carteira recuperarCarteiraCrypto(Cliente cliente, CryptoMoeda cryptoMoeda){

        return cliente.getCarteiras().stream()
                .filter(carteira -> carteira.getCryptoMoeda().equals(cryptoMoeda)).findAny()
                .orElseThrow(() -> new CarteiraNotFoundException("Usuario " + cliente.getUserName()
                        + "não tem carteira da crypto moeda " + cryptoMoeda.toString()));
    }

    private boolean verificarSaldo(Carteira carteira, BigDecimal valor){
       return carteira.getQuantidade().compareTo(valor) <= 0;
    }

    //---------------------------------------------
    private BigDecimal calcularValorTotalOrdem(Ordem ordem) {
        return calcularTaxaCorretagem(ordem).add(calcularValorOrdem(ordem));
    }

    private BigDecimal calcularTaxaCorretagem(Ordem ordem) {
        return calcularValorOrdem(ordem).multiply(TAXA_CORRETAGEM);
    }

    private BigDecimal calcularValorOrdem(Ordem ordem) {
        return ordem.getValorUnitario()
                .multiply(ordem.getQuantidade());
    }

//    private boolean aprovarSaldo(Ordem ordem, Cliente cliente){
//        return ordem.getValorTotal().compareTo(cliente.getConta().getSaldo())<=0;
//    }

    private void registrarVendaNoLivro(Ordem ordem, CryptoMoeda cryptoMoeda){
        switch (cryptoMoeda){
            case BITCOIN -> livroBitcoinService.saveOrdemVenda(ordem);
            case  ETHEREUM -> livroEthereumService.saveOrdemVenda(ordem);
            case CARDANO -> livroCardanoService.saveOrdemVenda(ordem);
            case SOLANA -> livroSolanaService.saveOrdemVenda(ordem);
        }
    }

    public List<OrdemDtoResponse> findAll() {
        return null;
    }
}
