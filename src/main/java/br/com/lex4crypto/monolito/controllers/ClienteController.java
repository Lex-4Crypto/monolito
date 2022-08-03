package br.com.lex4crypto.monolito.controllers;

import br.com.lex4crypto.monolito.dtos.ClienteDtoRequest;
import br.com.lex4crypto.monolito.dtos.ClienteDtoResponseCompleto;
import br.com.lex4crypto.monolito.dtos.ClienteDtoResponseSimples;
import br.com.lex4crypto.monolito.models.Cliente;
import br.com.lex4crypto.monolito.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PreAuthorize("permitAll")
    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteDtoRequest clienteDtoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.saveUsuario(clienteDtoRequest));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Cliente>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll());
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody @Valid ClienteDtoRequest usuarioDto) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.update(id, usuarioDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso!");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/form-simples")
    public ResponseEntity<List<ClienteDtoResponseSimples>> findAllFormularioSimples(){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAllFormularioSimples());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/form-simples/{id}")
    public ResponseEntity<ClienteDtoResponseSimples> findFormularioSimplesById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findFormularioSimplesById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/form-completo")
    public ResponseEntity<List<ClienteDtoResponseCompleto>> findAllFormularioCompleto(){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAllFormularioCompleto());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/form-completo/{id}")
    public ResponseEntity<ClienteDtoResponseCompleto> findFormularioCompletoById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findFormularioCompletoById(id));
    }

}
