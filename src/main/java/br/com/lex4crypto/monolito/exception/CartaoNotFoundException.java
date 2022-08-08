package br.com.lex4crypto.monolito.exception;

public class CartaoNotFoundException extends RuntimeException{
    public CartaoNotFoundException(String message) {
        super(message);
    }
}
