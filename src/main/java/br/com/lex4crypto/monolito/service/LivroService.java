package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.enums.CryptoMoeda;
import br.com.lex4crypto.monolito.models.Ordem;
import br.com.lex4crypto.monolito.models.Livro;
import br.com.lex4crypto.monolito.repositories.LivroRepository;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void salveLivro(Livro livro){
        livroRepository.save(livro);
    }

    public Livro findLivro(CryptoMoeda cryptoMoeda) {
        return livroRepository.findAll().stream()
                .filter(livro -> livro.getCryptoMoeda().equals(cryptoMoeda))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Livro não existe"));
    }

    public void saveOrdemNoLivro(Ordem ordem) {
        Livro livro =  findLivro(ordem.getCryptoMoeda());
        livro.getOrdens().add(ordem);
        livroRepository.save(livro);
    }
}
