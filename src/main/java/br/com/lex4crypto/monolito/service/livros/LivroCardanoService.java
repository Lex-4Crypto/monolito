package br.com.lex4crypto.monolito.service.livros;

import br.com.lex4crypto.monolito.models.Ordem;
import br.com.lex4crypto.monolito.models.livros.LivroCardano;
import br.com.lex4crypto.monolito.repositories.livros.LivroCardanoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroCardanoService {

    private final LivroCardanoRepository livroCardanoRepository;

    public LivroCardanoService(LivroCardanoRepository livroCardanoRepository) {
        this.livroCardanoRepository = livroCardanoRepository;
    }

    public void saveOrdemVenda(Ordem ordem){

        List<LivroCardano> all = livroCardanoRepository.findAll();
        LivroCardano livroCardano = all.stream().findFirst().orElseThrow(() -> new RuntimeException("Livro Cardano não existe"));
        livroCardano.getOrdens().add(ordem);
    }
}
