package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.CarteiraDto;
import br.com.lex4crypto.monolito.exception.CarteiraNotFoundException;
import br.com.lex4crypto.monolito.repositories.CarteiraRepository;
import br.com.lex4crypto.monolito.models.Carteira;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CarteiraService {
    private final CarteiraRepository carteiraRepository;

    public CarteiraService(CarteiraRepository carteiraRepository) {
        this.carteiraRepository = carteiraRepository;
    }

    @Transactional
    public Carteira save(Carteira carteira) {
        return carteiraRepository.save(carteira);
    }

    public List<Carteira> findAll() {
        return carteiraRepository.findAll();
    }

    public Carteira findById(Long id){
        return carteiraRepository
                .findById(id)
                .orElseThrow(CarteiraNotFoundException::new);
    }

    @Transactional
    public Carteira update(Long id, CarteiraDto carteiraDto){
        Carteira carteira = findById(id);
        carteira.setQuantidade(carteiraDto.getQuantidade());
        carteira.setCriptoMoeda(carteiraDto.getCriptoMoeda());
        return carteiraRepository.save(carteira);
    }

    @Transactional
    public void delete(Long id){
        Carteira carteira = findById(id);
        carteiraRepository.delete(carteira);
    }



}
