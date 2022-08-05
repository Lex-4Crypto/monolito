package br.com.lex4crypto.monolito.repositories.livros;

import br.com.lex4crypto.monolito.models.livros.LivroEthereum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroEthereumRepository extends JpaRepository<LivroEthereum, Long> {
}
