package com.kayo.libraryapi.repository;

import com.kayo.libraryapi.model.Autor;
import com.kayo.libraryapi.model.GeneroLivro;
import com.kayo.libraryapi.model.Livro;
import jakarta.persistence.ManyToOne;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    //Query Method
    //select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    //select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    List<Livro> findByGeneroAndPreco(GeneroLivro generoLivro, BigDecimal preco);


    //JPQL -> referencia as entidades e as propriedades
    // select l.* from livro as l order by l.titulo, l.preco
    @Query("select  l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloEPreco();

    @Query("select  a from Livro l join l.autor a")
    List<Autor> listarTodosAutorAndLivro();

    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesDeLivros();//como o titulo é uma string a List tem que retornar uma <String>

    //lidando com Querys maiores """..."""
    @Query("""
            select l.genero from Livro l join l.autor a where a.nascionalidade = 'Brasileiro'
            order by l.genero
            """)
    List<String> OrdenarPorNascionalidadeBrasileira();


    //Querys com parametros ---->>>
    @Query(" select l.genero from Livro l where l.genero = :generoParam")
    List<GeneroLivro> findByGenero(@Param("generoParam") GeneroLivro generoLivro);

    //Usando o Parametro para ordenar
    //named parameters --> parametros nomeados
    @Query("""
            select l from Livro l where l.genero = :genero order by :GeneroParam
            """)
    List<Livro> findByGenero(@Param("genero")
                             GeneroLivro generoLivro,
                             @Param("GeneroParam") String Escolha);//você pode trocar o String Escolha pelo Sort e usar o Sort.by("titulo")

    //positional parameters
    @Query("""
            select l from Livro l where l.genero = ?1 order by ?2
            """)
    List<Livro> findByGeneroPositionParameters(GeneroLivro generoLivro, String Escolha);

    @Modifying
    @Transactional
    @Query(" delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query(" update Livro set data_publicada = ?1") //Não é muito utilizado dessa forma, dessa forma, todos os livros são mudados para a data inserida. Para isso não acontecer utilizamos o where
    void updateDataPublicacao(LocalDate novaData);


}
