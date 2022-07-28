package br.com.lex4crypto.monolito.repositories;

import br.com.lex4crypto.monolito.models.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}
