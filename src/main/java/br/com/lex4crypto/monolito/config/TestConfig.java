package br.com.lex4crypto.monolito.config;

import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.models.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("it is working!");
    }
}
