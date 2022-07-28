package br.com.lex4crypto.monolito.service;

import br.com.lex4crypto.monolito.dtos.UsuarioDto;
import br.com.lex4crypto.monolito.models.Carteira;
import br.com.lex4crypto.monolito.models.Usuario;
import br.com.lex4crypto.monolito.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario saveUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id){
        Usuario usuario = usuarioRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException());
        return usuario;
    }

    @Transactional
    public Usuario update(Long id, UsuarioDto usuarioDto){
        Usuario usuario = findById(id);
        usuario.setNome(usuarioDto.getNome());
        usuario.setChavePix(usuarioDto.getChavePix());
        usuario.setCarteiras(usuarioDto.getCarteiras());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Long id){
        Usuario usuario = findById(id);
        usuarioRepository.delete(usuario);
    }



}
