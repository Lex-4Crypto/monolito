package br.com.lex4crypto.monolito.service.livros;

import br.com.lex4crypto.monolito.repositories.livros.LivroBitcoinRepository;
import org.springframework.stereotype.Service;

@Service
public class LivroBitcoinService implements LivroService{
    private final LivroBitcoinRepository livroBitcoinRepository;

    public LivroBitcoinService(LivroBitcoinRepository livroBitcoinRepository) {
        this.livroBitcoinRepository = livroBitcoinRepository;
    }
}
