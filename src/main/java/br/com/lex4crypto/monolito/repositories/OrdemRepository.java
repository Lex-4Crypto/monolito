package br.com.lex4crypto.monolito.repositories;

import br.com.lex4crypto.monolito.models.Ordem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdemRepository extends JpaRepository<Ordem, Long> {
}
