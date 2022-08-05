package br.com.lex4crypto.monolito.service.livros;

import br.com.lex4crypto.monolito.models.interfaces.Ordem;
import br.com.lex4crypto.monolito.models.livros.LivroEthereum;
import br.com.lex4crypto.monolito.models.livros.LivroSolana;
import br.com.lex4crypto.monolito.repositories.livros.LivroEthereumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroEthereumService {

    private final LivroEthereumRepository livroEthereumRepository;

    public LivroEthereumService(LivroEthereumRepository livroEthereumRepository) {
        this.livroEthereumRepository = livroEthereumRepository;
    }

    public void saveOrdemVenda(Ordem ordem){

        List<LivroEthereum> all = livroEthereumRepository.findAll();
        LivroEthereum livroEthereum = all.stream().findFirst().orElseThrow(() -> new RuntimeException("Livro Ethereum não existe"));
        livroEthereum.getOrdens().add(ordem);
    }
}
