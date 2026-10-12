package com.kayo.libraryapi.dto.request;

import java.util.List;

public record CadastrarUsuario(
        String login,
        String senha,
        List<String> roles
) {
}
