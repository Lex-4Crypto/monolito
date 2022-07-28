package br.com.lex4crypto.monolito.dtos;

import br.com.lex4crypto.monolito.models.Carteira;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class UsuarioDto {
        @NotNull
        private String nome;
        @NotNull
        private String chavePix;
        private List<Carteira> carteiras;

    }
