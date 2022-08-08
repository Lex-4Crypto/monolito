package br.com.lex4crypto.monolito.controllers;

import br.com.lex4crypto.monolito.dtos.CartaoDtoRequest;
import br.com.lex4crypto.monolito.models.Cartao;
import br.com.lex4crypto.monolito.service.CartaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<Cartao> save(@RequestBody @Valid CartaoDtoRequest cartaoDtoRequest){
        Cartao save = cartaoService.save(cartaoDtoRequest);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/cartoes").toUriString());
        return ResponseEntity.created(uri).body(save);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/usuarios/id/{id}")
    public ResponseEntity<List<Cartao>> findAllByClienteId(@PathVariable Long id){
        List<Cartao> cartoes = cartaoService.findAllByClienteId(id);
        return ResponseEntity.ok().body(cartoes);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/usuarios/username/{userName}")
    public ResponseEntity<List<Cartao>> findAllByUserName(@PathVariable String userName){
        List<Cartao> cartoes = cartaoService.findAllByUserName(userName);
        return ResponseEntity.ok().body(cartoes);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<Cartao>> findAll(){
        List<Cartao> cartoes = cartaoService.findAll();
        return ResponseEntity.ok().body(cartoes);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Cartao> delete(@PathVariable Long id){
        cartaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
