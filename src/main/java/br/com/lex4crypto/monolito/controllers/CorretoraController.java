package br.com.lex4crypto.monolito.controllers;

import br.com.lex4crypto.monolito.dtos.OrdemDtoRequest;
import br.com.lex4crypto.monolito.dtos.OrdemDtoResponse;
import br.com.lex4crypto.monolito.models.interfaces.Ordem;
import br.com.lex4crypto.monolito.service.CorretoraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/corretoras")
public class CorretoraController {

    private final CorretoraService corretoraService;

    public CorretoraController(CorretoraService corretoraService) {
        this.corretoraService = corretoraService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/ordem")
    ResponseEntity<OrdemDtoResponse> solicitarOrdem(@RequestBody @Valid OrdemDtoRequest ordemDtoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(corretoraService.solicitarOrdem(ordemDtoRequest));

    }

    @GetMapping
    ResponseEntity<List<OrdemDtoResponse>> findAllOrdem() {
        return ResponseEntity.status(HttpStatus.OK).body(corretoraService.findAll());
    }

     // findByID
    //findByClient ou Username
}
