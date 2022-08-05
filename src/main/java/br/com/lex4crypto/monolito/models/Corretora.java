package br.com.lex4crypto.monolito.models;

import br.com.lex4crypto.monolito.models.interfaces.Ordem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAnyAttribute;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Corretora {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double taxaCorretagem;

    private boolean validarTransacao(Ordem ordem, Cliente cliente){
        //ordem.executar(cliente);
        return false;
    }
}
