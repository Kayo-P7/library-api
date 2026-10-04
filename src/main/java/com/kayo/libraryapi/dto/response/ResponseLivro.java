package com.kayo.libraryapi.dto.response;

import com.kayo.libraryapi.model.Autor;
import com.kayo.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseLivro(

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
