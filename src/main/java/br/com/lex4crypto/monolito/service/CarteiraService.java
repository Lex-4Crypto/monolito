package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.CarteiraDtoRequest;
import br.com.lex4crypto.monolito.enums.StatusOrdem;
import br.com.lex4crypto.monolito.enums.TipoOrdem;
import br.com.lex4crypto.monolito.exception.CarteiraNotFoundException;
import br.com.lex4crypto.monolito.exception.DuplicatedDataException;
import br.com.lex4crypto.monolito.exception.UsuarioNotFoundException;
import br.com.lex4crypto.monolito.models.Cliente;
import br.com.lex4crypto.monolito.models.Ordem;
import br.com.lex4crypto.monolito.repositories.CarteiraRepository;
import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;
    private final ClienteRepository clienteRepository;

    public CarteiraService(CarteiraRepository carteiraRepository, ClienteRepository clienteRepository) {
        this.carteiraRepository = carteiraRepository;
        this.clienteRepository = clienteRepository;
    }


    @Transactional
    public Carteira save(CarteiraDtoRequest carteiraDtoRequest) {
        // verifica se existe cliente pelo userName
        Cliente cliente = clienteRepository.findByUserName(carteiraDtoRequest.getUserName())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado. Nome usuário: " + carteiraDtoRequest.getUserName()));

        //verifica se o cliente já possui a carteira
        List<Carteira> carteiras = findAllByUserName(cliente.getUserName());
        long count = carteiras.stream().
                filter(carteira -> carteira.getCryptoMoeda().equals(carteiraDtoRequest.getCryptoMoeda())).count();
        if(count > 0){
            throw new DuplicatedDataException("Cliente " + cliente.getNome() +
                    " já possui esta carteira. Carteira: " + carteiraDtoRequest.getCryptoMoeda().toString());
        }

        // cria  carteira se usuario existe e não possui
        Carteira carteira = new Carteira();
        carteira.setCryptoMoeda(carteiraDtoRequest.getCryptoMoeda());
        carteira.setQuantidade(carteiraDtoRequest.getQuantidade());
        Carteira save = carteiraRepository.save(carteira);

        // salva carteria no usuário
        cliente.getCarteiras().add(carteira);
        clienteRepository.save(cliente);

        // retorna carteria salva
        return save;
    }

    public List<Carteira> findAll() {
        return carteiraRepository.findAll();
    }

    public Carteira findById(Long id){
        return carteiraRepository
                .findById(id)
                .orElseThrow(() -> new CarteiraNotFoundException("Carteira não encontrada. Id: " + id));
    }

    public List<Carteira> findAllByClienteId(Long id){
        // procura cliente
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado. Id usuário: " + id));

        return cliente.getCarteiras();
    }

    public List<Carteira> findAllByUserName(String userName){
        // procura cliente
        Cliente cliente = clienteRepository.findByUserName(userName)
                .orElseThrow(() -> new UsuarioNotFoundException("Cliente não encontrado. Nome usuário: " + userName));

        return cliente.getCarteiras();
    }

    @Transactional
    public Carteira update(Long id, CarteiraDtoRequest carteiraDtoRequest){
        Carteira carteira = findById(id);
        carteira.setQuantidade(carteiraDtoRequest.getQuantidade());
        carteira.setCryptoMoeda(carteiraDtoRequest.getCryptoMoeda());
        return carteiraRepository.save(carteira);
    }

    @Transactional
    public void delete(Long id){
        Carteira carteira = findById(id);
        carteiraRepository.delete(carteira);
    }

    public Carteira recuperarCarteiraCliente(Ordem ordem){
        List<Carteira> carteiras = findAllByUserName(ordem.getUsernameCliente());
        Optional<Carteira> carteiraOptional = carteiras.stream()
                .filter(carteira -> carteira.getCryptoMoeda().equals(ordem.getCryptoMoeda()))
                .findAny();
        if (carteiraOptional.isPresent()){
            return carteiraOptional.get();
        }
        if (ordem.getTipoOrdem().equals(TipoOrdem.COMPRA)) {
            var carteiraDtoRequest = new CarteiraDtoRequest(ordem.getUsernameCliente(), ordem.getCryptoMoeda(), BigDecimal.ZERO);
            return save(carteiraDtoRequest);
        }
        throw (new CarteiraNotFoundException("Usuario " + ordem.getUsernameCliente()
                + "não tem carteira da crypto moeda " + ordem.getCryptoMoeda().toString()));
    }

    public Carteira atualizarQuantidadeCrypto(Carteira carteira, BigDecimal novaQuantidade) {
        carteira.setQuantidade(novaQuantidade);
        return carteiraRepository.save(carteira);
   }

}
