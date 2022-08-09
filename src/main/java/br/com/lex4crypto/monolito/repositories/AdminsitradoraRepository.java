package br.com.lex4crypto.monolito.repositories;

import br.com.lex4crypto.monolito.models.Administradora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminsitradoraRepository extends JpaRepository<Administradora, String> {
}
