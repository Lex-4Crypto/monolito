package br.com.lex4crypto.monolito.controllers;

import br.com.lex4crypto.monolito.dtos.UsuarioDtoRequest;
import br.com.lex4crypto.monolito.dtos.UsuarioDtoResponseCompleto;
import br.com.lex4crypto.monolito.dtos.UsuarioDtoResponseSimples;
import br.com.lex4crypto.monolito.models.Usuario;
import br.com.lex4crypto.monolito.service.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("permitAll")
    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody @Valid UsuarioDtoRequest usuarioDto){
        var usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDto, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveUsuario(usuario));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Usuario>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody @Valid UsuarioDtoRequest usuarioDto) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.update(id, usuarioDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletada com sucesso!");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/form-simples")
    public ResponseEntity<List<UsuarioDtoResponseSimples>> findAllFormularioSimples(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAllFormularioSimples());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/form-simples/{id}")
    public ResponseEntity<UsuarioDtoResponseSimples> findFormularioSimplesById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findFormularioSimplesById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/form-completo")
    public ResponseEntity<List<UsuarioDtoResponseCompleto>> findAllFormularioCompleto(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAllFormularioCompleto());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/form-completo/{id}")
    public ResponseEntity<UsuarioDtoResponseCompleto> findFormularioCompletoById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findFormularioCompletoById(id));
    }

}
