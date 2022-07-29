package br.com.lex4crypto.monolito.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerImpl {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CarteiraNotFoundException.class)
    public ResponseEntity<String> handleCarteiraNotFound(CarteiraNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<String> handleUsuarioNotFound(UsuarioNotFoundException ex) {
        return ResponseEntity.badRequest().body("Usuário não encontrado");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleValidationExceptions(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Informação inválida");
    }


}
