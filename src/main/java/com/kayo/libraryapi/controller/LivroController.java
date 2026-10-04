package com.kayo.libraryapi.controller;


import com.kayo.libraryapi.Services.AutorService;
import com.kayo.libraryapi.Services.LivroService;
import com.kayo.libraryapi.dto.request.AutorDTO;
import com.kayo.libraryapi.dto.request.CadastrarLivro;
import com.kayo.libraryapi.dto.response.ListExAutorDto;
import com.kayo.libraryapi.dto.response.ResponseLivro;
import com.kayo.libraryapi.model.Autor;
import com.kayo.libraryapi.model.Livro;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/livro")
public class LivroController {

   private LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<ResponseLivro> save(@RequestBody @Valid CadastrarLivro dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(dto));

    }
}
