package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.CarteiraDtoRequest;
import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.models.Ordem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrdemService {
    private final CarteiraService carteiraService;
    private final ClienteService clienteService;

    public OrdemService(CarteiraService carteiraService, ClienteService clienteService) {
        this.carteiraService = carteiraService;
        this.clienteService = clienteService;
    }


    //executar a ordem:

    //retornar a carteira que deverá ser preenchida
    public Carteira recuperarCarteiraCliente(Ordem ordem){
        List<Carteira> carteiras = carteiraService.findAllByUserName(ordem.getUsernameCliente());
        Optional<Carteira> carteiraOptional = carteiras.stream()
                .filter(carteira -> carteira.getCryptoMoeda().equals(ordem.getCryptoMoeda()))
                .findAny();
        if (carteiraOptional.isPresent()){
            return carteiraOptional.get();
        }
        var carteiraDtoRequest = new CarteiraDtoRequest(ordem.getUsernameCliente(), ordem.getCryptoMoeda(), BigDecimal.ZERO);
        return carteiraService.save(carteiraDtoRequest);
    }

}
