package br.com.lex4crypto.monolito.models;

import br.com.lex4crypto.monolito.enums.CryptoMoeda;
import br.com.lex4crypto.monolito.enums.StatusOrdem;
import br.com.lex4crypto.monolito.enums.TipoOrdem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ordem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    protected TipoOrdem tipoOrdem;
    @Enumerated(EnumType.STRING)
    protected CryptoMoeda cryptoMoeda;
    protected BigDecimal quantidade;
    protected BigDecimal valorUnitario;
    protected BigDecimal valorTaxaCorretagem;
    protected BigDecimal valorTotal;
    protected String usernameCliente;
    @Enumerated(EnumType.STRING)
    private StatusOrdem statusOrdem;
}
