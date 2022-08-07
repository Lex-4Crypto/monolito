package br.com.lex4crypto.monolito.controllers;

import br.com.lex4crypto.monolito.dtos.OrdemDtoRequest;
import br.com.lex4crypto.monolito.dtos.OrdemDtoResponse;
import br.com.lex4crypto.monolito.service.CorretoraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import javax.validation.Valid;
import java.net.URI;
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
        //return ResponseEntity.status(HttpStatus.CREATED).body(corretoraService.processarOrdem(ordemDtoRequest));
        return null;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/ordens/vendas")
    ResponseEntity<OrdemDtoResponse> lancarVenda(@RequestBody @Valid OrdemDtoRequest ordemDtoRequest) {
        OrdemDtoResponse response = corretoraService.lancarVenda(ordemDtoRequest);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("corretoras/ordens/vendas").toUriString());
        return ResponseEntity.created(uri).body(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/ordens/compras")
    ResponseEntity<OrdemDtoResponse> lancarCompra(@RequestBody @Valid OrdemDtoRequest ordemDtoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(corretoraService.lancarCompra(ordemDtoRequest));

//        OrdemDtoResponse response = corretoraService.lancarCompra(ordemDtoRequest);
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("corretoras/ordens/compras").toUriString());
//        return ResponseEntity.created(uri).body(response);
    }

 /*   @GetMapping
    ResponseEntity<List<OrdemDtoResponse>> findAllOrdem() {
        return ResponseEntity.status(HttpStatus.OK).body(corretoraService.findAll());
    }
 */
     // findByID
    //findByClient ou Username
}
