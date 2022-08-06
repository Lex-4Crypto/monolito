package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.OrdemDtoRequest;
import br.com.lex4crypto.monolito.dtos.OrdemDtoResponse;
import br.com.lex4crypto.monolito.enums.StatusOrdem;
import br.com.lex4crypto.monolito.enums.TipoOrdem;
import br.com.lex4crypto.monolito.exception.SaldoInsuficienteException;
import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.models.Cliente;
import br.com.lex4crypto.monolito.models.Ordem;
import br.com.lex4crypto.monolito.repositories.CorretoraRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CorretoraService {

    private final CorretoraRepository corretoraRepository;
    private final ClienteService clienteService;
    private final VendaService vendaService;
    private final LivroService livroService;
    private final CarteiraService carteiraService;
    private final OrdemService ordemService;

    private static final BigDecimal TAXA_CORRETAGEM = BigDecimal.valueOf(0.005);

    public CorretoraService(CorretoraRepository corretoraRepository, ClienteService clienteService, VendaService vendaService, LivroService livroService, CarteiraService carteiraService, OrdemService ordemService) {
        this.corretoraRepository = corretoraRepository;
        this.clienteService = clienteService;
        this.vendaService = vendaService;
        this.livroService = livroService;
        this.carteiraService = carteiraService;
        this.ordemService = ordemService;
    }

    public OrdemDtoResponse lancarVenda(OrdemDtoRequest ordemDtoRequest){

        //obter dados usuario
        Cliente cliente = clienteService.findClienteByUsername(ordemDtoRequest.getUsernameCliente());

        //criar ordem de venda
        Ordem ordemVenda = criarOrdem(ordemDtoRequest, TipoOrdem.VENDA);

        //validar pedido de venda
        Carteira carteiraCrypto = carteiraService.recuperarCarteiraCliente(ordemVenda);

        if(!aprovarQuantidadeCripto(carteiraCrypto,ordemVenda.getQuantidade())) {
            ordemService.atribuirStatus(ordemVenda, StatusOrdem.ERRO);
            throw new SaldoInsuficienteException("Saldo insuficiente. Saldo do cliente: " +
                    carteiraCrypto.getQuantidade() + " " + carteiraCrypto.getCryptoMoeda().toString());
        }


        //salvar nova quantidade na carteira
        BigDecimal novaQuantidadeCriptoCarteira = carteiraCrypto.getQuantidade().subtract(ordemVenda.getQuantidade());
        Carteira carteira = carteiraService.atualizarQuantidadeCrypto(carteiraCrypto, novaQuantidadeCriptoCarteira);

        //salvar pedido de venda
        Ordem ordemSalva = vendaService.save(ordemVenda);

        //salvar pedido de venda no livro
        livroService.saveOrdemNoLivro(ordemSalva);
        ordemService.atribuirStatus(ordemVenda, StatusOrdem.PENDENTE);

        //retorna DTO ordem de venda criada
        OrdemDtoResponse response = new OrdemDtoResponse();
        BeanUtils.copyProperties(ordemSalva, response);
        return response;
    }

    public Ordem processarOrdemCompra(OrdemDtoRequest ordemDtoRequest) {
        //verificar se há ordem de venda no livro
        //Se não retornar uma exceção

        //Recuperar cliente
        Cliente cliente = clienteService.findClienteByUsername(ordemDtoRequest.getUsernameCliente());
        Ordem ordemCompra = criarOrdem(ordemDtoRequest, TipoOrdem.COMPRA);

        //Executar a ordem
        if (aprovarSaldoConta(ordemCompra,cliente)){
            //retorna a carteira e acrescentar criptos
            Carteira carteira = carteiraService.recuperarCarteiraCliente(ordemCompra);
            BigDecimal quantidade = carteira.getQuantidade();
            carteira.setQuantidade(quantidade.add(ordemCompra.getQuantidade()));

            //salvar no histórico da corretora/cliente, se houver histórico
        } else {
            ordemService.atribuirStatus(ordemCompra, StatusOrdem.ERRO);
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }
        return null;
    }

    private Ordem criarOrdem(OrdemDtoRequest ordemDtoRequest, TipoOrdem tipoOrdem){
        Ordem ordem = new Ordem();
        BeanUtils.copyProperties(ordemDtoRequest, ordem);
        ordem.setTipoOrdem(tipoOrdem);
        ordem.setValorTaxaCorretagem(calcularTaxaCorretagem(ordem));
        ordem.setValorTotal(calcularValorTotalOrdem(ordem));
        ordem.setStatusOrdem(StatusOrdem.PENDENTE);
        return ordem;
    }

    private boolean aprovarQuantidadeCripto(Carteira carteira, BigDecimal quantidadeOrdem){
        return carteira.getQuantidade().compareTo(quantidadeOrdem) >= 0;
    }

    //---------------------------------------------
    private BigDecimal calcularValorTotalOrdem(Ordem ordem) {
        if (ordem.getTipoOrdem().equals(TipoOrdem.COMPRA)) {
            return calcularTaxaCorretagem(ordem).add(calcularValorOrdem(ordem));
        }
        return calcularValorOrdem(ordem).subtract(calcularTaxaCorretagem(ordem));
    }

    private BigDecimal calcularTaxaCorretagem(Ordem ordem) {
        return calcularValorOrdem(ordem).multiply(TAXA_CORRETAGEM);
    }

    private BigDecimal calcularValorOrdem(Ordem ordem) {
        return ordem.getValorUnitario()
                .multiply(ordem.getQuantidade());
    }

    private boolean aprovarSaldoConta(Ordem ordem, Cliente cliente){
        return ordem.getValorTotal().compareTo(cliente.getConta().getSaldo())<=0;
    }

    private boolean buscarOrdemVenda (Ordem ordem) {
        return false;
    }

    public List<OrdemDtoResponse> findAll() {
        return null;
    }
}
