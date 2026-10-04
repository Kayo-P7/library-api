package com.kayo.libraryapi.mapper;

import com.kayo.libraryapi.dto.request.CadastrarLivro;
import com.kayo.libraryapi.model.Livro;
import com.kayo.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    protected AutorRepository autorRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "autor", expression = "java(autorRepository.findById(cadastrarLivro.autorId()).orElseThrow(() -> new RuntimeException(\"Id not found\")) )")
    public abstract Livro toLivro(CadastrarLivro cadastrarLivro);
}
