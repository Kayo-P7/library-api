package com.kayo.libraryapi.controller;


import com.kayo.libraryapi.Services.LivroService;
import com.kayo.libraryapi.dto.request.CadastrarLivro;
import com.kayo.libraryapi.dto.response.LivroResponse;
import com.kayo.libraryapi.model.GeneroLivro;
import com.kayo.libraryapi.model.Livro;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/livro")
public class LivroController {

    private LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<LivroResponse> save(@RequestBody @Valid CadastrarLivro dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(dto));

    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'USER')")
    public ResponseEntity<LivroResponse> obterLivro(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(livroService.obterLivro(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable("id") UUID id) {

        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pesquisa")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'USER')")
    public ResponseEntity<Page<Livro>> pesquisa(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String nomeAutor,
            @RequestParam(required = false) GeneroLivro generoLivro,
            @RequestParam(required = false) Integer anoPublicacao,
            Pageable pageable
    ) {

        return ResponseEntity.ok()
                .body(livroService
                        .pesquisa(isbn, titulo, nomeAutor, generoLivro, anoPublicacao, pageable));

    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<LivroResponse> atualizar(@PathVariable("id") UUID id, @RequestBody @Valid CadastrarLivro cadastrarLivro) {

        return ResponseEntity.ok().body(livroService.atualizar(id, cadastrarLivro));
    }
}
