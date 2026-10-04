package com.kayo.libraryapi.dto.request;

import com.kayo.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AutorDTO(
        @NotBlank
        @Size(max = 100, min = 5, message = "campo obrigatorio")
        String nome,
        @NotNull(message = "campo obrigatorio")
        @Past(message = "A data de nascimento não pode ser uma data futura")
        LocalDate dataNascimento,
        @NotBlank
        @Size(min = 10, max = 50, message = "campo obrigatorio")
        String nacionalidade
) {

}
