package br.com.lex4crypto.monolito.dtos;

import br.com.lex4crypto.monolito.enums.CryptoMoeda;
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
public class OrdemDtoRequest {

//    @NotNull
//    private TipoOrdem tipoOrdem;
    @NotNull
    private CryptoMoeda cryptoMoeda;
    @NotNull
    private BigDecimal quantidade;
    @NotNull
    private BigDecimal valorUnitario;
    @NotNull
    private String usernameCliente;

}
