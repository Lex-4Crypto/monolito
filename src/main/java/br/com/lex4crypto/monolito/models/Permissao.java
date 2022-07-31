package br.com.lex4crypto.monolito.models;

import br.com.lex4crypto.monolito.enums.TipoPermissao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permissao implements GrantedAuthority { // implementa a interface GrantedAuthority

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TipoPermissao tipoPermissao;

    @Override
    public String getAuthority() {
        return this.tipoPermissao.toString();
    }
}
