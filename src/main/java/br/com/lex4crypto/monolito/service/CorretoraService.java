package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.OrdemDtoRequest;
import br.com.lex4crypto.monolito.dtos.OrdemDtoResponse;
import br.com.lex4crypto.monolito.models.interfaces.Ordem;
import br.com.lex4crypto.monolito.repositories.CorretoraRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CorretoraService {

    private final CorretoraRepository corretoraRepository;

    public CorretoraService(CorretoraRepository corretoraRepository) {
        this.corretoraRepository = corretoraRepository;
    }

    public OrdemDtoResponse solicitarOrdem(OrdemDtoRequest ordemDtoRequest) {
        //verificar se o usuário da ordem existe
        //informar valor da corretagem
        //verificar se o usuário possui saldo
        //efetuar compra
        //salvar no livro
        return null;
    }

    private BigDecimal calcularTaxaCorretagem(Ordem ordem) {
        return null;

    }

    public List<OrdemDtoResponse> findAll() {
        return null;
    }
}
