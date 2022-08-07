package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.ClienteDtoRequest;
import br.com.lex4crypto.monolito.dtos.ClienteDtoResponseCompleto;
import br.com.lex4crypto.monolito.dtos.ClienteDtoResponseSimples;
import br.com.lex4crypto.monolito.enums.TipoPermissao;
import br.com.lex4crypto.monolito.models.Cliente;
import br.com.lex4crypto.monolito.models.Permissao;
import br.com.lex4crypto.monolito.models.Usuario;
import br.com.lex4crypto.monolito.repositories.ClienteRepository;
import br.com.lex4crypto.monolito.repositories.PermissaoRepository;
import br.com.lex4crypto.monolito.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ClienteService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PermissaoRepository permissaoRepository;
    private final PasswordEncoder passwordEncoder;

    //metodo para carregar usuario do login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // recupera usuario do banco de dados
        Usuario usuario = usuarioRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found! Username: " + username));

        // retorna um usuario do banco dados como um user do spring security (construtor completo)
         return new User(usuario.getUsername(), usuario.getPassword(), true,
                                        true, true, true, usuario.getAuthorities());

    }

    public Cliente findClienteByUsername(String username){
        return clienteRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found! Username: " + username));
    }

    public Cliente saveUsuario(ClienteDtoRequest clienteDtoRequest){
        // cria novo Cliente e Usuario
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();

        //inserindo atributos do cliente
        cliente.setNome(clienteDtoRequest.getNome());
        cliente.setUserName(clienteDtoRequest.getUserName());
        cliente.getConta().setSaldo(BigDecimal.ZERO);
        String pixAleatorio = UUID.randomUUID().toString();
        cliente.setChavePix(pixAleatorio.substring(0, 20));

        //inserindo atributos do usuario
        usuario.setUserName(clienteDtoRequest.getUserName());
        String senhaEncriptada = passwordEncoder.encode(clienteDtoRequest.getSenha());
        usuario.setPassword(senhaEncriptada);
        Permissao permissaoUser = permissaoRepository.findByTipoPermissao(TipoPermissao.ROLE_USER);
        usuario.setAuthorities(List.of(permissaoUser));

        //salva cliente e usuario
        usuarioRepository.save(usuario);
        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id){
        return clienteRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Usuario não encontrado"));
    }

    public List<ClienteDtoResponseSimples> findAllFormularioSimples(){
        // recupera todos usuarios
        List<Cliente> todosUsuarios = findAll();

        //converte usuário para Dto Response simples
        return todosUsuarios.stream()
                .map(usuario -> {
                    ClienteDtoResponseSimples response = new ClienteDtoResponseSimples();
                    BeanUtils.copyProperties(usuario, response);
                    return response;
                }).toList();
    }

    public ClienteDtoResponseSimples findFormularioSimplesById(Long id){
        Cliente cliente = findById(id);
        ClienteDtoResponseSimples response = new ClienteDtoResponseSimples();
        BeanUtils.copyProperties(cliente, response);
        return response;
    }

    public List<ClienteDtoResponseCompleto> findAllFormularioCompleto(){
        // recupera todos usuarios
        List<Cliente> todosClientes = findAll();
        //converte usuário para Dto Response simples
        return todosClientes.stream()
                .map(cliente -> {
                    ClienteDtoResponseCompleto response = new ClienteDtoResponseCompleto();
                    BeanUtils.copyProperties(cliente, response);
                    return response;
                }).toList();
    }

    public ClienteDtoResponseCompleto findFormularioCompletoById(Long id){
        Cliente cliente = findById(id);
        ClienteDtoResponseCompleto response = new ClienteDtoResponseCompleto();
        BeanUtils.copyProperties(cliente, response);
        return response;
    }

    public Cliente update(Long id, ClienteDtoRequest clienteDtoRequest){
        //recupera cliente e usuario
        Cliente cliente = findById(id);
        Usuario usuario = usuarioRepository.findByUserName(cliente.getUserName()).get();
        //update cliente
        cliente.setNome(clienteDtoRequest.getNome());
        cliente.setUserName(clienteDtoRequest.getUserName());
        //update usuario
        usuario.setUserName(clienteDtoRequest.getUserName());
        usuario.setPassword(passwordEncoder.encode(clienteDtoRequest.getSenha()));
        //salva cliente e usuario
        usuarioRepository.save(usuario);
        return clienteRepository.save(cliente);
    }

    public void delete(Long id){
        Cliente cliente = findById(id);
        clienteRepository.delete(cliente);
    }

    public void updateNovoSaldo(Cliente cliente, BigDecimal novoSaldo){
        cliente.getConta().setSaldo(novoSaldo);
        clienteRepository.save(cliente);
    }

}
