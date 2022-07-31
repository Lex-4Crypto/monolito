package br.com.lex4crypto.monolito.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class UsuarioDtoRequest {
        @NotNull
        private String nome;
        @NotNull
        private String nomeUsuario;
        @NotNull
        private String senha;
        @NotNull
        private String chavePix;
    }
