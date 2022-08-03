package br.com.lex4crypto.monolito.repositories;

import br.com.lex4crypto.monolito.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByUserName(String userName);
}
