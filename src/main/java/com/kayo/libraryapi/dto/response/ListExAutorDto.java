package com.kayo.libraryapi.dto.response;

import com.kayo.libraryapi.model.Autor;

import java.util.List;

public record ListExAutorDto(
     List<Autor> listAutores
) {
}
