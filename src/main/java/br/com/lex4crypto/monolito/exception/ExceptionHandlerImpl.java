package br.com.lex4crypto.monolito.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerImpl {
    @ExceptionHandler(CarteiraNotFoundException.class)
    public ResponseEntity<String> handlePatrocinadorNotFound(CarteiraNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<String> handleUsuarioNotFound(UsuarioNotFoundException ex) {
        return ResponseEntity.badRequest().body("Usuário não encontrado");
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleValidationExceptions(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Informação inválida");
    }


}
