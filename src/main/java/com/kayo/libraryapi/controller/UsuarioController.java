package com.kayo.libraryapi.controller;

import com.kayo.libraryapi.Services.UsuarioService;
import com.kayo.libraryapi.dto.request.CadastrarUsuario;
import com.kayo.libraryapi.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public void salvar(@RequestBody CadastrarUsuario cadastrarUsuario) {
        usuarioService.salvar(cadastrarUsuario);
    }

    @GetMapping
    public Usuario findByUser(String login){
        return usuarioService.findByUser(login);
    }
}
