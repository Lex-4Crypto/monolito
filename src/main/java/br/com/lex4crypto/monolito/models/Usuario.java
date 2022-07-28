package br.com.lex4crypto.monolito.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String chavePix;

    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn (name = "carteira_id")
    private List<Carteira> carteiras;

    @OneToOne
    @JoinColumn(name = "carteira_principal_id")
    private Carteira carteiraPrincipal;
}
