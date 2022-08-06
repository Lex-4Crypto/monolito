package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.CarteiraDtoRequest;
import br.com.lex4crypto.monolito.enums.StatusOrdem;
import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.models.Ordem;
import br.com.lex4crypto.monolito.repositories.OrdemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrdemService {
    private final OrdemRepository ordemRepository;

    public OrdemService(OrdemRepository ordemRepository) {
        this.ordemRepository = ordemRepository;
    }

    public void atribuirStatus(Ordem ordem, StatusOrdem statusOrdem){
        ordem.setStatusOrdem(statusOrdem);
        ordemRepository.save(ordem);
    }


    //executar a ordem:

    //retornar a carteira que deverá ser preenchida


}
