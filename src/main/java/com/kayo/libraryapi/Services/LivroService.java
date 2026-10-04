package com.kayo.libraryapi.Services;

import com.kayo.libraryapi.dto.request.AutorDTO;
import com.kayo.libraryapi.dto.request.CadastrarLivro;
import com.kayo.libraryapi.dto.response.ListExAutorDto;
import com.kayo.libraryapi.dto.response.ResponseLivro;
import com.kayo.libraryapi.mapper.LivroMapper;
import com.kayo.libraryapi.model.Autor;
import com.kayo.libraryapi.model.Livro;
import com.kayo.libraryapi.repository.AutorRepository;
import com.kayo.libraryapi.repository.LivroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class LivroService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    public LivroService(AutorRepository autorRepository, LivroRepository livroRepository, LivroMapper livroMapper) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
        this.livroMapper = livroMapper;
    }

    public ResponseLivro save(CadastrarLivro cadastrarLivro) {
        Livro livro = new Livro();
        livro = livroMapper.toLivro(cadastrarLivro);
        livroRepository.save(livro);


        return new ResponseLivro(
                livro.getIsbn(),
                livro.getTitulo(),
                livro.getData_publicada(),
                livro.getGenero(),
                livro.getDataCadastro(),
                livro.getDataAtualizacao(),
                livro.getPreco(),
                livro.getAutor()
        );
    }

}
