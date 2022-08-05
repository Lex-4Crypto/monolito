package br.com.lex4crypto.monolito.repositories.livros;

import br.com.lex4crypto.monolito.models.livros.LivroBitcoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroBitcoinRepository extends JpaRepository<LivroBitcoin, Long> {
}
