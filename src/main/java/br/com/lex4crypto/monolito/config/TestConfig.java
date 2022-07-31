package br.com.lex4crypto.monolito.config;

import br.com.lex4crypto.monolito.enums.TipoPermissao;
import br.com.lex4crypto.monolito.models.Permissao;
import br.com.lex4crypto.monolito.models.Usuario;
import br.com.lex4crypto.monolito.repositories.PermissaoRepository;
import br.com.lex4crypto.monolito.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@Profile("test")
@Slf4j
public class TestConfig implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PermissaoRepository permissaoRepository;
    private final PasswordEncoder passwordEncoder;

    public TestConfig(UsuarioRepository usuarioRepository, PermissaoRepository permissaoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.permissaoRepository = permissaoRepository;
        this.passwordEncoder = passwordEncoder;
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
        user1.setNome("joao");
        user1.setNomeUsuario("joao123");
        user1.setSenha(passwordEncoder.encode("1212"));
        user1.setPermissoes(List.of(permissaoUser));

        Usuario user2 = new Usuario();
        user2.setNome("kleber");
        user2.setNomeUsuario("kleber123");
        user2.setSenha(passwordEncoder.encode("1212"));
        user2.setPermissoes(List.of(permissaoAdmin, permissaoUser));

        usuarioRepository.save(user1);
        usuarioRepository.save(user2);

    }
}
