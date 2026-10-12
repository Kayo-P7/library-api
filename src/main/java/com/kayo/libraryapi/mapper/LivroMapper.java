package com.kayo.libraryapi.mapper;

import com.kayo.libraryapi.dto.request.CadastrarLivro;
import com.kayo.libraryapi.dto.response.LivroResponse;
import com.kayo.libraryapi.model.Livro;
import com.kayo.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    protected AutorRepository autorRepository;

    public abstract LivroResponse toLivroResponse(Livro livro);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "autor", expression = "java(autorRepository.findById(cadastrarLivro.autorId()).orElseThrow(() -> new RuntimeException(\"Id not found\")) )")
    public abstract Livro toLivro(CadastrarLivro cadastrarLivro);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "autor", ignore = true)
    public abstract void atualizarLivro(
            CadastrarLivro cadastrarLivro,
            @MappingTarget Livro livro
    );
}
