package br.com.lex4crypto.monolito.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean //Restrições
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest().authenticated().and().httpBasic();

        return http.build();
    }

    @Bean //Usuário (em memória)
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("1212"))
                .roles("USER") //Permissões
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("1212"))
                .roles("USER", "ADMIN") //Permissões
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean //Criptografia
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}