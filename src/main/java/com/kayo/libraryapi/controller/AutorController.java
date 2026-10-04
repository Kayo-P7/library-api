package com.kayo.libraryapi.controller;


import com.kayo.libraryapi.Services.AutorService;
import com.kayo.libraryapi.dto.request.AutorDTO;
import com.kayo.libraryapi.dto.response.ListExAutorDto;
import com.kayo.libraryapi.model.Autor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/autorId")
public class AutorController implements GenericController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid AutorDTO dto) {

        Autor autor = autorService.save(dto);

        URI url = geradorHeaderLocation(autor.getId());

        return ResponseEntity.created(url).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscar(@PathVariable("id") String id) {


        return autorService.buscarAutor(id).
                map(autor -> ResponseEntity.ok(autor)).
                orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<ListExAutorDto>pesquisa(
            @RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {


        return ResponseEntity.ok().body(autorService.pesquisaByExample(nome, nacionalidade));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {
        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
