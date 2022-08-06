package br.com.lex4crypto.monolito.controllers;

import br.com.lex4crypto.monolito.models.Ordem;
import br.com.lex4crypto.monolito.service.OrdemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ordens")
public class OrdemController {

    private final OrdemService ordemService;

    public OrdemController(OrdemService ordemService) {
        this.ordemService = ordemService;
    }

    @GetMapping
    public ResponseEntity<List<Ordem>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(ordemService.findAll());
    }

    @GetMapping("/vendas")
    public ResponseEntity<List<Ordem>> findAllVendas(){
        return ResponseEntity.status(HttpStatus.OK).body(ordemService.findVendas());
    }

    @GetMapping("/compras")
    public ResponseEntity<List<Ordem>> findAllCompras(){
        return ResponseEntity.status(HttpStatus.OK).body(ordemService.findCompras());
    }

    @GetMapping("/status/pendentes")
    public ResponseEntity<List<Ordem>> findAllPendentes(){
        return ResponseEntity.status(HttpStatus.OK).body(ordemService.findPendentes());
    }

    @GetMapping("/status/erro")
    public ResponseEntity<List<Ordem>> findAllErros(){
        return ResponseEntity.status(HttpStatus.OK).body(ordemService.findErros());
    }

    @GetMapping("/status/concluidas")
    public ResponseEntity<List<Ordem>> findAllConcluidas(){
        return ResponseEntity.status(HttpStatus.OK).body(ordemService.findConcluidas());
    }
}
