package com.kayo.libraryapi.dto.request;

import com.kayo.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastrarLivro(
        @ISBN
        @NotBlank
        String isbn,
        @NotBlank
        String titulo,
        @NotNull
        @Past
        LocalDate data_publicada,
        @NotNull
        GeneroLivro genero,
        @NotNull
        BigDecimal preco,
        @NotNull
        UUID autorId
) {
}
