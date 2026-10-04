package com.kayo.libraryapi.Services;

import com.kayo.libraryapi.dto.request.AutorDTO;
import com.kayo.libraryapi.dto.response.ListExAutorDto;
import com.kayo.libraryapi.mapper.AutorMapper;
import com.kayo.libraryapi.model.Autor;
import com.kayo.libraryapi.repository.AutorRepository;
import com.kayo.libraryapi.repository.LivroRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final AutorMapper autorMapper;

    public AutorService(AutorRepository autorRepository, LivroRepository livroRepository, AutorMapper autorMapper) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
        this.autorMapper = autorMapper;
    }

    public Autor save(AutorDTO dto) {
        Autor autor = new Autor();

        autor = autorMapper.toAutor(dto);
        return autorRepository.save(autor);

    }


    public Optional<AutorDTO> buscarAutor(String id) {

        UUID UDA = UUID.fromString(id);
        return autorRepository.findById(UDA).map(autor ->
                new AutorDTO(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade())
        );
    }

    public ListExAutorDto pesquisaByExample(String nome, String nacionalidade) {

        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);


        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id,")
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);


        Example<Autor> autorExample = Example.of(
                autor, matcher
        );

        List<Autor> list = autorRepository.findAll(autorExample);

        return new ListExAutorDto(list);
    }

    public void delete(UUID id) {
        Autor autor = autorRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
        if (autorExist(autor)) {
            new RuntimeException("Autor have a book is not deleted autorId");
            return;
        }

        autorRepository.deleteById(id);
    }

    public boolean autorExist(Autor autor) {

        return livroRepository.existsByAutor(autor);

    }
}
