package br.com.lex4crypto.monolito.service.livros;

import br.com.lex4crypto.monolito.models.Ordem;
import br.com.lex4crypto.monolito.models.livros.LivroBitcoin;
import br.com.lex4crypto.monolito.repositories.livros.LivroBitcoinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroBitcoinService {

    private final LivroBitcoinRepository livroBitcoinRepository;

    public LivroBitcoinService(LivroBitcoinRepository livroBitcoinRepository) {
        this.livroBitcoinRepository = livroBitcoinRepository;
    }

    public void saveOrdemVenda(Ordem ordem){

        List<LivroBitcoin> all = livroBitcoinRepository.findAll();
        LivroBitcoin livroBitcoin = all.stream().findFirst().orElseThrow(() -> new RuntimeException("Livro Bitcoin não existe"));
        livroBitcoin.getOrdens().add(ordem);
    }
}
