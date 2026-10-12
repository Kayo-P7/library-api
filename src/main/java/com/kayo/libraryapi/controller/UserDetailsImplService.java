package com.kayo.libraryapi.controller;

import com.kayo.libraryapi.Services.UsuarioService;
import com.kayo.libraryapi.model.Usuario;
import com.kayo.libraryapi.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImplService implements UserDetailsService {

    private UsuarioService usuarioService;

    public UserDetailsImplService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByUser(username);
        return new UserDetailsImpl(usuario);
    }
}
