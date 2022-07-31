package br.com.lex4crypto.monolito.dtos;

import br.com.lex4crypto.monolito.models.Conta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDtoResponseSimples {

    private String nome;
    private String nomeUsuario;
    private String chavePix;
    private Conta conta;
}
