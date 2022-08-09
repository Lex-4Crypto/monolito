package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.OrdemDtoRequest;
import br.com.lex4crypto.monolito.dtos.OrdemDtoResponse;
import br.com.lex4crypto.monolito.enums.StatusOrdem;
import br.com.lex4crypto.monolito.enums.TipoOrdem;
import br.com.lex4crypto.monolito.exception.SaldoInsuficienteException;
import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.models.Cliente;
import br.com.lex4crypto.monolito.models.Ordem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CorretoraService {

    private final ClienteService clienteService;
    private final VendaService vendaService;
    private final LivroService livroService;
    private final CarteiraService carteiraService;
    private final OrdemService ordemService;

    private static final BigDecimal TAXA_CORRETAGEM = BigDecimal.valueOf(0.005);

    public CorretoraService(ClienteService clienteService, VendaService vendaService, LivroService livroService, CarteiraService carteiraService, OrdemService ordemService) {
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
        carteiraService.atualizarQuantidadeCrypto(carteiraCrypto, novaQuantidadeCriptoCarteira);

        //salvar pedido de venda
        Ordem ordemSalva = vendaService.save(ordemVenda);

        //salvar pedido de venda no livro
        livroService.saveOrdemNoLivro(ordemSalva);

        //retorna DTO ordem de venda criada
        OrdemDtoResponse response = new OrdemDtoResponse();
        BeanUtils.copyProperties(ordemSalva, response);
        return response;
    }

    public OrdemDtoResponse lancarCompra(OrdemDtoRequest ordemDtoRequest) {

        //Recuperar cliente
        Cliente clienteCompra = clienteService.findClienteByUsername(ordemDtoRequest.getUsernameCliente());
        Ordem ordemCompra = criarOrdem(ordemDtoRequest, TipoOrdem.COMPRA);

        //Verificar se há ordem de venda compatível no livro
        Ordem ordemVenda = ordemService.findOrdemVendaCompativelCompra(ordemCompra);

        //Executar a ordem
        if (aprovarSaldoConta(ordemCompra,clienteCompra)){
            //Atualizar carteira do cliente que comprou com o novo saldo
            Carteira carteira = carteiraService.recuperarCarteiraCliente(ordemCompra);
            BigDecimal novaQuantidadeCriptoCarteira = ordemCompra.getQuantidade().add(carteira.getQuantidade());
            carteiraService.atualizarQuantidadeCrypto(carteira, novaQuantidadeCriptoCarteira);

            //Atualizar conta do cliente que comprou com o novo saldo
            BigDecimal novoSaldoClienteCompra = clienteCompra
                    .getConta()
                    .getSaldo()
                    .subtract(calcularValorTotalOrdem(ordemCompra));
            clienteService.updateNovoSaldo(clienteCompra, novoSaldoClienteCompra);

            //Atualizar conta do cliente que vendeu com o novo saldo
            String usernameClienteVenda = ordemVenda.getUsernameCliente();
            Cliente clienteVenda = clienteService.findClienteByUsername(usernameClienteVenda);
            BigDecimal novoSaldoClienteVenda = clienteVenda
                    .getConta()
                    .getSaldo()
                    .add(ordemVenda.getValorTotal());
            clienteService.updateNovoSaldo(clienteVenda, novoSaldoClienteVenda);

            //Atualizar status das Ordens
            ordemService.atribuirStatus(ordemVenda, StatusOrdem.CONCLUIDA);
            ordemService.atribuirStatus(ordemCompra, StatusOrdem.CONCLUIDA);

            //Atualizar ordens
            Ordem ordemSalvaCompra = vendaService.save(ordemCompra);
            vendaService.save(ordemVenda);


            //Atualizar livro
            livroService.saveOrdemNoLivro(ordemCompra);
            livroService.saveOrdemNoLivro(ordemVenda);

            //retorna DTO ordem de venda criada
            OrdemDtoResponse response = new OrdemDtoResponse();
            BeanUtils.copyProperties(ordemSalvaCompra, response);
            return response;

        } else {
            ordemService.atribuirStatus(ordemCompra, StatusOrdem.ERRO);
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }
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

}
