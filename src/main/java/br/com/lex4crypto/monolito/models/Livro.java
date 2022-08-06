package br.com.lex4crypto.monolito.models;

import br.com.lex4crypto.monolito.enums.CryptoMoeda;
import br.com.lex4crypto.monolito.models.Ordem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Livro {

    @Id
    @Enumerated(EnumType.STRING)
    private CryptoMoeda cryptoMoeda;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id")
    protected List<Ordem> ordens;
}
