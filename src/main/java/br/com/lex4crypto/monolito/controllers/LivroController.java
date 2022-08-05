package br.com.lex4crypto.monolito.controllers;

import br.com.lex4crypto.monolito.service.livros.LivroBitcoinService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroBitcoinService livroBitcoinService;

    public LivroController(LivroBitcoinService livroBitcoinService) {
        this.livroBitcoinService = livroBitcoinService;
    }
}
