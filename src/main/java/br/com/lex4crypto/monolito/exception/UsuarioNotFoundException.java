package br.com.lex4crypto.monolito.exception;

public class UsuarioNotFoundException extends RuntimeException{

    public UsuarioNotFoundException(String message) {
        super(message);
    }
}
