package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.CartaoDtoRequest;
import br.com.lex4crypto.monolito.dtos.CarteiraDtoRequest;
import br.com.lex4crypto.monolito.exception.CartaoNotFoundException;
import br.com.lex4crypto.monolito.exception.CarteiraNotFoundException;
import br.com.lex4crypto.monolito.exception.DuplicatedDataException;
import br.com.lex4crypto.monolito.exception.UsuarioNotFoundException;
import br.com.lex4crypto.monolito.models.Cartao;
import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.models.Cliente;
import br.com.lex4crypto.monolito.repositories.CartaoRepository;
import br.com.lex4crypto.monolito.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartaoService {
    private final CartaoRepository cartaoRepository;
    private final ClienteRepository clienteRepository;

    public CartaoService(CartaoRepository cartaoRepository,ClienteRepository clienteRepository) {
        this.cartaoRepository = cartaoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Cartao save(CartaoDtoRequest cartaoDtoRequest) {
        // verifica se existe cliente pelo userName
        Cliente cliente = clienteRepository.findByUserName(cartaoDtoRequest.getUserName())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado. Nome usuário: " + cartaoDtoRequest.getUserName()));

        //verifica se o cliente já possui o cartão
        List<Cartao> cartoes = findAllByUserName(cliente.getUserName());
        long count = cartoes.stream()
                .filter(cartao -> cartao
                        .getNumero()
                        .equals(cartaoDtoRequest.getNumero())
                )
                .count();
        if(count > 0){
            throw new DuplicatedDataException("Cliente " + cliente.getNome() +
                    " já possui este cartão. Cartão: " + cartaoDtoRequest.getNumero());
        }

        //cria  cartão se usuario existe e não possui
        Cartao cartao = new Cartao();
        cartao.setNumero(cartaoDtoRequest.getNumero());
        cartao.setNomeTitular(cartaoDtoRequest.getNomeTitular());
        Cartao save = cartaoRepository.save(cartao);

        // salva cartao no usuário
        cliente.getCartoes().add(cartao);
        clienteRepository.save(cliente);

        // retorna cartão salvo
        return save;
    }

    @Transactional
    public void delete(Long id){
        Cartao cartao = findById(id);
        cartaoRepository.delete(cartao);
    }

    public List<Cartao> findAll() {
        return cartaoRepository.findAll();
    }

    public Cartao findById(Long id){
        return cartaoRepository
                .findById(id)
                .orElseThrow(() -> new CartaoNotFoundException("Cartão não encontrado. Id: " + id));
    }

    public List<Cartao> findAllByClienteId(Long id){
        // procura cliente
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado. Id usuário: " + id));

        return cliente.getCartoes();
    }

    public List<Cartao> findAllByUserName(String userName){
        // procura cliente
        Cliente cliente = clienteRepository.findByUserName(userName)
                .orElseThrow(() -> new UsuarioNotFoundException("Cliente não encontrado. Nome usuário: " + userName));

        return cliente.getCartoes();
    }


}
