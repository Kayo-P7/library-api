package com.kayo.libraryapi.Services;

import com.kayo.libraryapi.dto.request.CadastrarUsuario;
import com.kayo.libraryapi.mapper.UsuarioMapper;
import com.kayo.libraryapi.model.Usuario;
import com.kayo.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    public void salvar(CadastrarUsuario cadastrarUsuario) {
        var senha = cadastrarUsuario.senha();
        var encode = passwordEncoder.encode(senha);
        Usuario usuario = usuarioMapper.toUsuario(cadastrarUsuario);
        usuario.setSenha(encode);
        usuarioRepository.save(usuario);

    }


    public Usuario findByUser(String login){
        return usuarioRepository.findByLogin(login).orElseThrow(() -> new RuntimeException("Usuario not found"));
    }

}
