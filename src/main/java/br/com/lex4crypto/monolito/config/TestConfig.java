package br.com.lex4crypto.monolito.config;

import br.com.lex4crypto.monolito.enums.CryptoMoeda;
import br.com.lex4crypto.monolito.enums.TipoPermissao;
import br.com.lex4crypto.monolito.models.*;
import br.com.lex4crypto.monolito.models.livros.LivroBitcoin;
import br.com.lex4crypto.monolito.models.livros.LivroCardano;
import br.com.lex4crypto.monolito.models.livros.LivroEthereum;
import br.com.lex4crypto.monolito.models.livros.LivroSolana;
import br.com.lex4crypto.monolito.repositories.ClienteRepository;
import br.com.lex4crypto.monolito.repositories.PermissaoRepository;
import br.com.lex4crypto.monolito.repositories.UsuarioRepository;
import br.com.lex4crypto.monolito.repositories.livros.LivroBitcoinRepository;
import br.com.lex4crypto.monolito.repositories.livros.LivroCardanoRepository;
import br.com.lex4crypto.monolito.repositories.livros.LivroEthereumRepository;
import br.com.lex4crypto.monolito.repositories.livros.LivroSolanaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@Profile("test")
@Slf4j
public class TestConfig implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PermissaoRepository permissaoRepository;
    private final PasswordEncoder passwordEncoder;
    private final LivroBitcoinRepository livroBitcoinRepository;
    private final LivroEthereumRepository livroEthereumRepository;
    private final LivroCardanoRepository livroCardanoRepository;
    private final LivroSolanaRepository livroSolanaRepository;


    private final ClienteRepository clienteRepository;

    public TestConfig(UsuarioRepository usuarioRepository, PermissaoRepository permissaoRepository, PasswordEncoder passwordEncoder, LivroBitcoinRepository livroBitcoinRepository, LivroEthereumRepository livroEthereumRepository, LivroCardanoRepository livroCardanoRepository, LivroSolanaRepository livroSolanaRepository, ClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.permissaoRepository = permissaoRepository;
        this.passwordEncoder = passwordEncoder;
        this.livroBitcoinRepository = livroBitcoinRepository;
        this.livroEthereumRepository = livroEthereumRepository;
        this.livroCardanoRepository = livroCardanoRepository;
        this.livroSolanaRepository = livroSolanaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("It's working!");

        //população banco de dados
        Permissao permissao1 = new Permissao(null, TipoPermissao.ROLE_USER);
        Permissao permissao2 = new Permissao(null, TipoPermissao.ROLE_ADMIN);
        permissaoRepository.save(permissao1);
        permissaoRepository.save(permissao2);

        Permissao permissaoUser = permissaoRepository.findByTipoPermissao(TipoPermissao.ROLE_USER);
        Permissao permissaoAdmin = permissaoRepository.findByTipoPermissao(TipoPermissao.ROLE_ADMIN);

        Usuario user1 = new Usuario();
        user1.setUserName("joao123");
        user1.setPassword(passwordEncoder.encode("1212"));
        user1.setAuthorities(List.of(permissaoUser));

        Usuario user2 = new Usuario();
        user2.setUserName("kleber123");
        user2.setPassword(passwordEncoder.encode("1212"));
        user2.setAuthorities(List.of(permissaoAdmin, permissaoUser));

        usuarioRepository.save(user1);
        usuarioRepository.save(user2);

        Carteira carteiraBitcoin = new Carteira(null, CryptoMoeda.BITCOIN,BigDecimal.TEN);

        Cliente cliente1 = new Cliente();
        cliente1.setNome("joao");
        cliente1.setUserName(user1.getUsername());
        cliente1.setChavePix("1443216414");
        cliente1.setConta(new Conta(null,"Real", BigDecimal.TEN));
        cliente1.getCarteiras().add(carteiraBitcoin);

        clienteRepository.save(cliente1);

        LivroBitcoin livroBitcoin = new LivroBitcoin(null);
        livroBitcoinRepository.save(livroBitcoin);
        LivroEthereum livroEthereum = new LivroEthereum(null);
        livroEthereumRepository.save(livroEthereum);
        LivroCardano livroCardano = new LivroCardano(null);
        livroCardanoRepository.save(livroCardano);
        LivroSolana livroSolana = new LivroSolana(null);
        livroSolanaRepository.save(livroSolana);
    }
}
