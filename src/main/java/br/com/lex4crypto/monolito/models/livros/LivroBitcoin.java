package br.com.lex4crypto.monolito.models.livros;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LivroBitcoin extends Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}