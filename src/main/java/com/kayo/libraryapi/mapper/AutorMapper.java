package com.kayo.libraryapi.mapper;

import com.kayo.libraryapi.dto.request.AutorDTO;
import com.kayo.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {


    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(source = "nacionalidade", target = "nacionalidade")
    Autor toAutor(AutorDTO dto);

    AutorDTO toDto(Autor autor);
}
