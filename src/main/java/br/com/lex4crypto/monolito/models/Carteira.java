package br.com.lex4crypto.monolito.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Carteira {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer criptoMoeda;

    private BigDecimal quantidade;

    public void setCriptoMoeda(CriptoMoeda criptoMoeda){
        if(criptoMoeda != null) {
            this.criptoMoeda = criptoMoeda.getIdentificador();
        }
    }

}
