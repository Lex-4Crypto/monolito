package br.com.lex4crypto.monolito.models.interfaces;

import br.com.lex4crypto.monolito.models.Usuario;

public interface Ordem {
    public void executar(Usuario usuario);
}
