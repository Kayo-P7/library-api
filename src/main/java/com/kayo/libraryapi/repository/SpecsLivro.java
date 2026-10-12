package com.kayo.libraryapi.repository;

import com.kayo.libraryapi.model.GeneroLivro;
import com.kayo.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class

SpecsLivro {


    public static Specification<Livro> isbnEqual(String isbn) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn));
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genero"), genero);

    }

    public static Specification<Livro> PublicacaoEqual(Integer anoPublicacao) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.function("to_char", String.class, root.get("data_publicada"), criteriaBuilder.literal("YYYY")), anoPublicacao.toString());


    }

    public static Specification<Livro> nomeAutorEqual(String nome) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%"));
    }

    public static Specification<Livro> AutorLike(String nome) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }

    public static Specification<Livro> NomeAutorLike(String nome){
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase()  + "%");
        };
    }

}
