package br.com.lex4crypto.monolito.service.livros;

import br.com.lex4crypto.monolito.models.interfaces.Ordem;
import br.com.lex4crypto.monolito.models.livros.LivroCardano;
import br.com.lex4crypto.monolito.models.livros.LivroSolana;
import br.com.lex4crypto.monolito.repositories.livros.LivroSolanaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroSolanaService {

    private final LivroSolanaRepository livroSolanaRepository;

    public LivroSolanaService(LivroSolanaRepository livroSolanaRepository) {
        this.livroSolanaRepository = livroSolanaRepository;
    }

    public void saveOrdemVenda(Ordem ordem){

        List<LivroSolana> all = livroSolanaRepository.findAll();
        LivroSolana livroSolana = all.stream().findFirst().orElseThrow(() -> new RuntimeException("Livro Solana não existe"));
        livroSolana.getOrdens().add(ordem);
    }

}
