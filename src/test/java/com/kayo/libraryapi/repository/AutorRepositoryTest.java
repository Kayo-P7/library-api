package com.kayo.libraryapi.repository;

import com.kayo.libraryapi.model.Autor;
import com.kayo.libraryapi.model.GeneroLivro;
import com.kayo.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    public AutorRepository autorRepository;


    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void salvar() {
        Autor autor = new Autor();
        autor.setNome("Kayden Ford-P.A");
        autor.setNascionalidade("Brasileiro");
        autor.setData_nascimento(LocalDate.of(2006, 06, 18));

        var autorSalvo = autorRepository.save(autor);

        System.out.println("Autor: " + autor);
    }


    @Test
    public void update() {

        var id = UUID.fromString("667a0f3f-5212-4bc7-af85-9f4ae8835541");

        Optional<Autor> FindAutor = autorRepository.findById(id);
        if (FindAutor.isPresent()) {

            var AutorEncontrado = FindAutor.get();
            System.out.println("Autor encontrado:");
            System.out.println(AutorEncontrado);
            AutorEncontrado.setNome("Kayden Albert-P.A");
            autorRepository.save(AutorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> lista = autorRepository.findAll();

        lista.forEach(System.out::println);
    }


    @Test
    public void deletarTest() {
        var id = UUID.fromString("3b75f99d-7afb-4fa2-a9cb-483e671e9581");

        Optional<Autor> autorEncontrado = autorRepository.findById(id);
        if (autorEncontrado.isPresent()) {
            System.out.println("Autor encontrado: ");
            System.out.println(autorEncontrado.get());
            System.out.println("autor deletado!");
            autorRepository.deleteById(id);

        }
    }
//ter muito cuidado com o cascade, porque você pode acabar deletando tudo cascade = cascadetype.all


    @Test
    public void SalvarAutorELivro() {

        Autor autor = new Autor();
        autor.setNome("Smile.A.S");
        autor.setData_nascimento(LocalDate.of(1980, 07, 20));
        autor.setNascionalidade("Argentino");

        Livro livro = new Livro();
        livro.setIsnb("770098-89903");
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setData_publicada(LocalDate.of(2016, 05, 12));
        livro.setTitulo("Trying again!");
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsnb("9450098-89903");
        livro2.setGenero(GeneroLivro.BIOGRAFIA);
        livro2.setPreco(BigDecimal.valueOf(200));
        livro2.setData_publicada(LocalDate.of(2021, 05, 12));
        livro2.setTitulo("Not cry!");
        livro2.setAutor(autor);

        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);
        autorRepository.save(autor);
        // livroRepository.saveAll(autor.getLivros());  //Com o cascade não precisaria utilizar essa linha

    }

    @Test
    @Transactional
    void ListarLivroAutor() {

        UUID id = UUID.fromString("ca9fdc9c-f437-45d3-8b84-192f29b27207");
        var autor = autorRepository.findById(id).get();

        List<Livro> livroList = livroRepository.findByAutor(autor);
        autor.setLivros(livroList);

//       //essa é a melhor forma para ver os livros do autor
//        var buscarPorLivro = livroRepository.findByAutor(autor);
//        buscarPorLivro.forEach(System.out::println);
        autor.getLivros().forEach(System.out::println);


    }

    @Test
    public void pesquisarPorTitulo(){
        String titulo = "TO VALUE TIME!";
        System.out.println("Achar o livro pelo titulo: ");
        List<Livro> listTitulo = livroRepository.findByTitulo(titulo);
        listTitulo.forEach(System.out::println);

    }

    @Test
    public void pesquisarPorGeneroAndPreco(){

        List<Livro> listTitulo = livroRepository.findByGeneroAndPreco(GeneroLivro.CIENCIA, BigDecimal.valueOf(150));
        System.out.println("Achar o livro pelo Genero e preço: ");
        listTitulo.forEach(System.out::println);

    }
}
