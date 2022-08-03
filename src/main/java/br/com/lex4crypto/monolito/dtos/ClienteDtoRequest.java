package br.com.lex4crypto.monolito.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ClienteDtoRequest {
        @NotNull
        @Size(min = 3, max = 50)
        private String nome;
        @NotNull
        @Size(min = 3, max = 50)
        private String userName;
        @NotNull
        @Size(min = 3, max = 50)
        private String senha;
    }
