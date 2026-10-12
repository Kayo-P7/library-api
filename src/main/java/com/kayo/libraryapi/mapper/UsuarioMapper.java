package com.kayo.libraryapi.mapper;

import com.kayo.libraryapi.dto.request.CadastrarUsuario;
import com.kayo.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {


    Usuario toUsuario(CadastrarUsuario cadastrarUsuario);
}
