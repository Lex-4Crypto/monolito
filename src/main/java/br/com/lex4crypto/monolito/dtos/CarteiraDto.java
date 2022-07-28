package br.com.lex4crypto.monolito.dtos;

import br.com.lex4crypto.monolito.models.CriptoMoeda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarteiraDto {
    @NotNull
    private CriptoMoeda criptoMoeda;

    private BigDecimal quantidade;

}
