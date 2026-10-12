package com.kayo.libraryapi.repository;

import com.kayo.libraryapi.model.Autor;
import com.kayo.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);
    List<Autor> findByNacionalidade(String nacionalidade);
    List<Autor> findByNome(String nome);

}
