package br.com.lex4crypto.monolito.dtos;

import br.com.lex4crypto.monolito.enums.CryptoMoeda;
import br.com.lex4crypto.monolito.enums.StatusOrdem;
import br.com.lex4crypto.monolito.enums.TipoOrdem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdemDtoResponse {

    private TipoOrdem tipoOrdem;
    private CryptoMoeda cryptoMoeda;
    private BigDecimal quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTaxaCorretagem;
    private BigDecimal valorTotal;
    private StatusOrdem statusOrdem;
}
