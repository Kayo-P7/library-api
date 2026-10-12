package com.kayo.libraryapi.dto.response;

import com.kayo.libraryapi.model.Autor;
import com.kayo.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record LivroResponse(

        String isbn,
        String titulo,
        LocalDate data_publicada,
        GeneroLivro genero,
        LocalDateTime dataCadastro,
        LocalDateTime dataAtualizacao,
        BigDecimal preco,
        Autor autor
) {

}
