package br.com.lex4crypto.monolito.models;

import br.com.lex4crypto.monolito.models.interfaces.Ordem;

public class Corretora {
    private boolean validarTransacao(Ordem ordem, Usuario usuario){
        ordem.executar(usuario);
        return false;
    }
}
