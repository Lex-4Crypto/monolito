package br.com.lex4crypto.monolito.controllers;

import br.com.lex4crypto.monolito.service.LivroService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroService livroService;

    public LivroController(LivroService livroBitcoinService) {
        this.livroService = livroBitcoinService;
    }
}
