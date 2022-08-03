package br.com.lex4crypto.monolito.controllers;

import br.com.lex4crypto.monolito.service.CarteiraService;
import br.com.lex4crypto.monolito.dtos.CarteiraDtoRequest;
import br.com.lex4crypto.monolito.models.Carteira;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {
    private final CarteiraService carteiraService;

    public CarteiraController(CarteiraService carteiraService) {
        this.carteiraService = carteiraService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<Carteira> save(@RequestBody @Valid CarteiraDtoRequest carteiraDtoRequest){
        Carteira carteira = carteiraService.save(carteiraDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(carteira);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<Carteira>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(carteiraService.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<Carteira> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(carteiraService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/clientes/{id}")
    public ResponseEntity<List<Carteira>> findByClienteId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(carteiraService.findAllByClienteId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Carteira> update(@PathVariable Long id, @RequestBody @Valid CarteiraDtoRequest carteiraDtoRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(carteiraService.update(id, carteiraDtoRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        carteiraService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Carteira deletada com sucesso!");
    }

}
