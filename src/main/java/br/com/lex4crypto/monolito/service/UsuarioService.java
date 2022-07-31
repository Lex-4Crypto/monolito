package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.UsuarioDto;
import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.models.CriptoMoeda;
import br.com.lex4crypto.monolito.models.Usuario;
import br.com.lex4crypto.monolito.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UsuarioService implements UserDetailsService {

    final UsuarioRepository usuarioRepository;

    //metodo para carregar usuario do login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // recupera usuario do banco de dados
        Usuario usuario = usuarioRepository.findByNomeUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found! Username: " + username));

        // retorna um usuario do banco dados como um user do spring security (construtor completo)
         return new User(usuario.getUsername(), usuario.getPassword(), true,
                                        true, true, true, usuario.getAuthorities());

    }

    public Usuario saveUsuario(Usuario usuario){

        // cria carteiras de crypto
        for (int i = 1; i <= CriptoMoeda.values().length; i++){
            usuario.getCarteiras().add(new Carteira(null, i, BigDecimal.ZERO));
        }

        //cria carteira principal (id = null (db cria) / moeda = "Real")
        usuario.getConta().setSaldo(BigDecimal.ZERO);

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id){
        Usuario usuario = usuarioRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Usuario não encontrado"));
        return usuario;
    }

    public Usuario update(Long id, UsuarioDto usuarioDto){
        Usuario usuario = findById(id);
        usuario.setNome(usuarioDto.getNome());
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id){
        Usuario usuario = findById(id);
        usuarioRepository.delete(usuario);
    }

}
