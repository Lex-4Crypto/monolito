package br.com.lex4crypto.monolito.models;

import br.com.lex4crypto.monolito.enums.CryptoMoeda;
import br.com.lex4crypto.monolito.enums.TipoOrdem;
import br.com.lex4crypto.monolito.models.interfaces.Ordem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdemVenda extends Ordem {

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
