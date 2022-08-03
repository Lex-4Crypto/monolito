package br.com.lex4crypto.monolito.models;

import br.com.lex4crypto.monolito.models.interfaces.Ordem;

import java.math.BigDecimal;

public class OrdemCompra implements Ordem {

    private Integer criptoMoeda;
    private BigDecimal valorCriptoMoeda;
    private Double quantidade;

    @Override
    public void executar(Cliente cliente) {
        BigDecimal saldoUsuario = cliente.getConta().getSaldo();
        if (valorCriptoMoeda.compareTo(saldoUsuario)<0){
            //Não pode executar
        }else {
            //executar a ordem
        }
    }
}
