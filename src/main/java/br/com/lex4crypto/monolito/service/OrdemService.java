package br.com.lex4crypto.monolito.service;


import br.com.lex4crypto.monolito.enums.StatusOrdem;
import br.com.lex4crypto.monolito.enums.TipoOrdem;
import br.com.lex4crypto.monolito.models.Ordem;
import br.com.lex4crypto.monolito.repositories.OrdemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Ordem> findAll() {
        return ordemRepository.findAll();
    }

    public List<Ordem> findVendas() {
        List<Ordem> ordens = findAll();
        return ordens.stream()
                .filter(ordem -> ordem.getTipoOrdem().equals(TipoOrdem.VENDA))
                .toList();
    }

    public List<Ordem> findCompras() {
        List<Ordem> ordens = findAll();
        return ordens.stream()
                .filter(ordem -> ordem.getTipoOrdem().equals(TipoOrdem.COMPRA))
                .toList();
    }

    public List<Ordem> findPendentes() {
        List<Ordem> ordens = findAll();
        return ordens.stream()
                .filter(ordem -> ordem.getStatusOrdem().equals(StatusOrdem.PENDENTE))
                .toList();
    }

    public List<Ordem> findErros() {
        List<Ordem> ordens = findAll();
        return ordens.stream()
                .filter(ordem -> ordem.getStatusOrdem().equals(StatusOrdem.ERRO))
                .toList();
    }

    public List<Ordem> findConcluidas() {
        List<Ordem> ordens = findAll();
        return ordens.stream()
                .filter(ordem -> ordem.getStatusOrdem().equals(StatusOrdem.CONCLUIDA))
                .toList();
    }

    //executar a ordem:

    //retornar a carteira que deverá ser preenchida

}
