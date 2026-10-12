package com.kayo.libraryapi.Services;

import com.kayo.libraryapi.dto.request.CadastrarLivro;
import com.kayo.libraryapi.dto.response.LivroResponse;
import com.kayo.libraryapi.exception.AutorNotFound;
import com.kayo.libraryapi.mapper.LivroMapper;
import com.kayo.libraryapi.mapper.LivroMapperImpl;
import com.kayo.libraryapi.model.Autor;
import com.kayo.libraryapi.model.GeneroLivro;
import com.kayo.libraryapi.model.Livro;
import com.kayo.libraryapi.repository.AutorRepository;
import com.kayo.libraryapi.repository.LivroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.kayo.libraryapi.repository.SpecsLivro.*; // importanto os métodos da classe SpecsLivro

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

    public LivroResponse save(CadastrarLivro cadastrarLivro) {
        Livro livro = new Livro();
        livro = livroMapper.toLivro(cadastrarLivro);
        livroRepository.save(livro);


        return new LivroResponse(
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


    public LivroResponse obterLivro(UUID id) {
        return livroRepository.findById(id).map(livroMapper::toLivroResponse).orElseThrow(() -> new RuntimeException("Not found"));

    }

    public void deletarLivro(UUID id) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
        if (livro.getAutor() != null) {
            throw new RuntimeException("Not possible deleted to book than contains autor ");
        }
        livroRepository.deleteById(id);
    }

    public Page<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro generoLivro,
                                Integer anoPublicacao,
                                Pageable pageable) {

        Specification<Livro> specification = Specification
                .where
                        ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());


        if (isbn != null) {
            specification = specification.and(isbnEqual(isbn));
        }

        if (titulo != null) {
            specification = specification.and(tituloLike(titulo));
        }

        if (generoLivro != null) {
            specification = specification.and(generoEqual(generoLivro));
        }

        if (anoPublicacao != null) {
            specification = specification.and(PublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null) {
            specification = specification.and(NomeAutorLike(nomeAutor));
        }


        return livroRepository.findAll(specification, pageable);

    }


    public LivroResponse atualizar(UUID id, CadastrarLivro cadastrarLivro) {

        Livro livro = livroRepository.findById(id).orElseThrow(() -> new RuntimeException("Não encontrado"));

        livroMapper.atualizarLivro(cadastrarLivro, livro);

        Autor autor = autorRepository.findById(cadastrarLivro.autorId()).orElseThrow(() -> new AutorNotFound("Autor não encontrado"));
        livro.setAutor(autor);

        livroRepository.save(livro);
        return livroMapper.toLivroResponse(livro);

    }
}
