package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.CarteiraDtoRequest;
import br.com.lex4crypto.monolito.exception.CarteiraNotFoundException;
import br.com.lex4crypto.monolito.exception.UsuarioNotFoundException;
import br.com.lex4crypto.monolito.models.Usuario;
import br.com.lex4crypto.monolito.repositories.CarteiraRepository;
import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CarteiraService {
    private final CarteiraRepository carteiraRepository;
    private final UsuarioRepository usuarioRepository;

    public CarteiraService(CarteiraRepository carteiraRepository, UsuarioRepository usuarioRepository) {
        this.carteiraRepository = carteiraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Carteira save(CarteiraDtoRequest carteiraDtoRequest) {
        // procura usuario
        Usuario usuario = usuarioRepository.findByNomeUsuario(carteiraDtoRequest.getNomeUsuario())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado. Nome usuário: " + carteiraDtoRequest.getNomeUsuario()));

        // se usuario existe, cria carteira
        Carteira carteira = new Carteira();
        carteira.setCriptoMoeda(carteiraDtoRequest.getCriptoMoeda());
        carteira.setQuantidade(carteiraDtoRequest.getQuantidade());
        Carteira save = carteiraRepository.save(carteira);

        // salva carteria no usuário
        usuario.getCarteiras().add(carteira);
        usuarioRepository.save(usuario);

        // retorna carteria salva
        return save;
    }

    public List<Carteira> findAll() {
        return carteiraRepository.findAll();
    }

    public Carteira findById(Long id){
        return carteiraRepository
                .findById(id)
                .orElseThrow(CarteiraNotFoundException::new);
    }

    public List<Carteira> findAllByUsuarioId(Long id){
        // procura usuario
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado. Id usuário: " + id));

        return usuario.getCarteiras();
    }

    @Transactional
    public Carteira update(Long id, CarteiraDtoRequest carteiraDtoRequest){
        Carteira carteira = findById(id);
        carteira.setQuantidade(carteiraDtoRequest.getQuantidade());
        carteira.setCriptoMoeda(carteiraDtoRequest.getCriptoMoeda());
        return carteiraRepository.save(carteira);
    }

    @Transactional
    public void delete(Long id){
        Carteira carteira = findById(id);
        carteiraRepository.delete(carteira);
    }

}
