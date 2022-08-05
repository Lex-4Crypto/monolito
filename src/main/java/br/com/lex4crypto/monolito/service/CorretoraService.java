package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.OrdemDtoRequest;
import br.com.lex4crypto.monolito.dtos.OrdemDtoResponse;
import br.com.lex4crypto.monolito.enums.TipoOrdem;
import br.com.lex4crypto.monolito.exception.SaldoInsuficienteException;
import br.com.lex4crypto.monolito.models.Cliente;
import br.com.lex4crypto.monolito.models.OrdemCompra;
import br.com.lex4crypto.monolito.models.OrdemVenda;
import br.com.lex4crypto.monolito.models.Usuario;
import br.com.lex4crypto.monolito.models.interfaces.Ordem;
import br.com.lex4crypto.monolito.repositories.CorretoraRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CorretoraService {

    private final CorretoraRepository corretoraRepository;
    private final ClienteService clienteService;
    private static final BigDecimal TAXA_CORRETAGEM = BigDecimal.valueOf(0.005); //Pode ser substituído pelo consumo de uma API

    public CorretoraService(CorretoraRepository corretoraRepository, ClienteService clienteService) {
        this.corretoraRepository = corretoraRepository;
        this.clienteService = clienteService;
    }

    public OrdemDtoResponse processarOrdem(OrdemDtoRequest ordemDtoRequest) {

        //Recuperar cliente
        Cliente cliente = clienteService.findClienteByUsername(ordemDtoRequest.getUsernameCliente());

        //Atribuir tipo da ordem
        Ordem ordem = verificarTipoOrdem(ordemDtoRequest);

        //Atribuir valor total da corretagem
        ordem.setValorTaxaCorretagem(calcularTaxaCorretagem(ordem));

        //Atribuir valor total da ordem
        ordem.setValorTotal(calcularValorTotalOrdem(ordem));

        //Executar a ordem
        if (aprovarSaldo(ordem,cliente)){
            ordem.executar(cliente);
        }else {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        return null;
    }

    public Ordem verificarTipoOrdem(OrdemDtoRequest ordemDtoRequest){
        if (ordemDtoRequest.getTipoOrdem().equals(TipoOrdem.VENDA)){
            OrdemVenda ordemVenda = new OrdemVenda();
            BeanUtils.copyProperties(ordemDtoRequest, ordemVenda);
            return ordemVenda;
        }else {
            OrdemCompra ordemCompra = new OrdemCompra();
            BeanUtils.copyProperties(ordemDtoRequest, ordemCompra);
            return ordemCompra;
        }
    }

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

    private boolean aprovarSaldo(Ordem ordem, Cliente cliente){
        return ordem.getValorTotal().compareTo(cliente.getConta().getSaldo())<=0;
    }

    public List<OrdemDtoResponse> findAll() {
        return null;
    }
}
