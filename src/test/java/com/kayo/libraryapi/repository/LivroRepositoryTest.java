package com.kayo.libraryapi.repository;

import com.kayo.libraryapi.model.Autor;
import com.kayo.libraryapi.model.GeneroLivro;
import com.kayo.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {


    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarLivro() {

        Livro livro = new Livro();


        livro.setIsbn("77098-83945");
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(BigDecimal.valueOf(150));
        livro.setData_publicada(LocalDate.of(2019, 6, 27));
        livro.setTitulo("No Fantasy");
        Autor autor = autorRepository.findById(UUID.fromString("02fea7ff-c306-4a9f-be9b-72aab8a57694")).orElse(null);
        livro.setAutor(autor);
        livroRepository.save(livro);
    }


    @Test
    public void salvarCascateLivro() {

        Livro livro = new Livro();


        livro.setIsbn("77098-89903");
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setPreco(BigDecimal.valueOf(150));
        livro.setData_publicada(LocalDate.of(2012, 12, 27));
        livro.setTitulo("TO VALUE TIME!");

        Autor autor = new Autor();
        autor.setNome("Kayden Albert");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2000, 07, 23));
        //Com o cascade o autorId vai ser salvo junto, mas isso não é muito utilizado em projetos profissionais
        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    public void salvarLivroAndAutor() {

        Livro livro = new Livro();


        livro.setIsbn("6845098-89903");
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(BigDecimal.valueOf(150));
        livro.setData_publicada(LocalDate.of(2019, 10, 27));
        livro.setTitulo("No Fantaty!");

        Autor autor = new Autor();
        autor.setNome("Kayden Vincyt");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2000, 07, 23));
        //Com o cascade o auto
        // vai ser salvo junto, mas isso não é muito utilizado em projetos profissionais
       // autorRepository.save(autorId);
        //livro.setAutor(autorId);

        livroRepository.save(livro);
    }

    @Test
    @Transactional
    //faz com que o autorId seja carregado, porque no ManyToOne da classe livro está como LAZY e só estava carregando o livro. Se fosse Eager carregaria os dois
    public void buscarLivro() {
        var id = UUID.fromString("5414e702-d6c9-4243-9677-c237af9ddb13");
        Livro livro = livroRepository.findById(id).orElse(null);

        System.out.println("Titulo:");
        System.out.println(livro.getTitulo());
        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }


    @Test
    public void update() {
        UUID id = UUID.fromString("5414e702-d6c9-4243-9677-c237af9ddb13");
        var AlterarLivro = livroRepository.findById(id).orElse(null);

        UUID autorId = UUID.fromString("18c85754-8377-4a44-96a5-8a72d427a4bf");
        Autor autor = autorRepository.findById(autorId).orElse(null);

        AlterarLivro.setTitulo("BATMAN YEAR 1");
        AlterarLivro.setAutor(autor);
        livroRepository.save(AlterarLivro);


    }

    @Test
    public void DeleteLivro() {
        UUID idDelete = UUID.fromString("eef80178-2185-405a-96bb-41fe3a043330");

        var LivroDelete = livroRepository.findById(idDelete).orElse(null);

        livroRepository.delete(LivroDelete);
    }

    @Test
    public void listTituloAndPreco() {
        var l = livroRepository.listarTodosOrdenadoPorTituloEPreco();
        l.forEach(System.out::println);
    }

    @Test
    public void listAutorAndLivro() {
        var l = livroRepository.listarTodosAutorAndLivro();
        l.forEach(System.out::println);
        //Só mostra os autores que tem livros
    }

    @Test
    public void listTitulosDiferentes() {
        var l = livroRepository.listarNomesDiferentesDeLivros();
        l.forEach(System.out::println);
        //Só mostra livros com titulos diferentes
    }

    @Test
    public void listOrdenandoPorNascionalidadeBrasileira() {
        var l = livroRepository.OrdenarPorNascionalidadeBrasileira();
        l.forEach(System.out::println);
        //Só mostra o genero de livros de autores Brasileiros
    }

    @Test
    public void buscaByGenero() {
        var l = livroRepository.findByGenero(GeneroLivro.FANTASIA);
        l.forEach(System.out::println);

        //busca por genero do livro com query @Param
    }

    @Test
    public void buscaByGeneroByTitulo() {
        var l = livroRepository.findByGenero(GeneroLivro.FANTASIA);
        l.forEach(System.out::println);

        //busca por genero do livro com query @Param
    }

    @Test
    public void buscaByGeneroByTituloOrdenandoPorTitulo() {
        var L = livroRepository.findByGenero(GeneroLivro.CIENCIA, "titulo");
        L.forEach(System.out::println);

        //busca por genero do livro com query @Param e ordenando por Titutlo
    }

    @Test
    public void buscaByGeneroByTituloOrdenandoPorTituloPositionPameteres() {
        var L = livroRepository.findByGeneroPositionParameters(GeneroLivro.CIENCIA, "titulo");
        L.forEach(System.out::println);

        //busca por genero do livro com query position parameters
    }

    @Test
    public void deleteGenero(){
        livroRepository.deleteByGenero(GeneroLivro.FANTASIA);
    }

    @Test
    public void UpdateDataPublicadaGeral(){
        livroRepository.updateDataPublicacao(LocalDate.of(2000, 10 , 7));
    }
}

