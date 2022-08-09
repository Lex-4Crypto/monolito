package br.com.lex4crypto.monolito.config;

import br.com.lex4crypto.monolito.enums.CryptoMoeda;
import br.com.lex4crypto.monolito.enums.TipoPermissao;
import br.com.lex4crypto.monolito.models.*;
import br.com.lex4crypto.monolito.repositories.ClienteRepository;
import br.com.lex4crypto.monolito.repositories.PermissaoRepository;
import br.com.lex4crypto.monolito.repositories.UsuarioRepository;
import br.com.lex4crypto.monolito.repositories.LivroRepository;
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
    private final LivroRepository livroRepository;

    private final ClienteRepository clienteRepository;

    public TestConfig(UsuarioRepository usuarioRepository, PermissaoRepository permissaoRepository, PasswordEncoder passwordEncoder, LivroRepository livroRepository, ClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.permissaoRepository = permissaoRepository;
        this.passwordEncoder = passwordEncoder;
        this.livroRepository = livroRepository;
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
        Carteira carteiraBitcoin2 = new Carteira(null, CryptoMoeda.BITCOIN,BigDecimal.valueOf(20.0));
        Carteira carteiraSolana = new Carteira(null, CryptoMoeda.SOLANA,BigDecimal.valueOf(15.0));

        Cliente cliente1 = new Cliente();
        cliente1.setNome("joao");
        cliente1.setUserName(user1.getUsername());
        cliente1.setChavePix("1443216414");
        cliente1.setConta(new Conta(null,"Real", BigDecimal.valueOf(1000.00)));
        cliente1.getCarteiras().add(carteiraBitcoin);
        cliente1.getCarteiras().add(carteiraSolana);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("maria");
        cliente2.setUserName(user2.getUsername());
        cliente2.setChavePix("1515615");
        cliente2.setConta(new Conta(null,"Real", BigDecimal.valueOf(2000.00)));
        cliente2.getCarteiras().add(carteiraBitcoin2);

        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);

        //Quando for subir para operação tem que criar um livro para cada cripto
        Livro livroBitcoin = new Livro(CryptoMoeda.BITCOIN, null);
        livroRepository.save(livroBitcoin);

        Livro livroEth = new Livro(CryptoMoeda.ETHEREUM, null);
        livroRepository.save(livroEth);

        Livro livroSolana = new Livro(CryptoMoeda.SOLANA, null);
        livroRepository.save(livroSolana);

        Livro livroCardano = new Livro(CryptoMoeda.CARDANO, null);
        livroRepository.save(livroCardano);
    }
}
