package br.com.lex4crypto.monolito.dtos;

import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.models.Conta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDtoResponseCompleto {

    private String nome;
    private String nomeUsuario;
    private String chavePix;
    private Conta conta;
    private List<Carteira> carteiras;
}
