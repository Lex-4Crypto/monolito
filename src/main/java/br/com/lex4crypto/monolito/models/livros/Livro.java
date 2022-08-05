package br.com.lex4crypto.monolito.models.livros;

import br.com.lex4crypto.monolito.models.Ordem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Livro {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "livro_id")
    protected List<Ordem> ordens;
}
