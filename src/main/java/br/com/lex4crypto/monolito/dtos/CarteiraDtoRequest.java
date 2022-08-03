package br.com.lex4crypto.monolito.dtos;

import br.com.lex4crypto.monolito.enums.CryptoMoeda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarteiraDtoRequest {

    @NotNull
    private String userName;
    @NotNull
    private CryptoMoeda cryptoMoeda;
    @NotNull
    private BigDecimal quantidade;

}
