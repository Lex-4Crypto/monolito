package br.com.lex4crypto.monolito.repositories;

import br.com.lex4crypto.monolito.models.interfaces.Ordem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Ordem, Long> {
}
