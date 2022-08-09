package br.com.lex4crypto.monolito.controllers;

import br.com.lex4crypto.monolito.models.Administradora;
import br.com.lex4crypto.monolito.service.AdministradoraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/administradoras")
public class AdministradoraController {
    private final AdministradoraService administradoraService;

    public AdministradoraController(AdministradoraService administradoraService) {
        this.administradoraService = administradoraService;
    }

    @GetMapping("/lucro/corretagens")
    public ResponseEntity<Administradora> informarLucroCorretagem(){
        return ResponseEntity.status(HttpStatus.OK).body(administradoraService.atualizarLucroCorretagem());
    }
}
