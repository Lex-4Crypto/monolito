package br.com.lex4crypto.monolito.repositories;

import br.com.lex4crypto.monolito.models.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
}
