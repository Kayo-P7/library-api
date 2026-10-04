package com.kayo.libraryapi.Services;

import com.kayo.libraryapi.model.Autor;
import com.kayo.libraryapi.model.GeneroLivro;
import com.kayo.libraryapi.model.Livro;
import com.kayo.libraryapi.repository.AutorRepository;
import com.kayo.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacoesService {

    @Autowired
    LivroRepository livroRepository;
    @Autowired
    AutorRepository autorRepository;


    @Transactional
    public void atualizarSemAtualizar(){
        var Livro = livroRepository.findById(UUID.fromString("2319b7c5-f9f0-45aa-9770-ce2f5ffadca0")).orElse(null);


        Livro.setData_publicada(LocalDate.of(2024,6,1));

    }

    @Transactional
    public void executar(){


        Autor autor = new Autor();
        autor.setNome("Vincy");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1513, 06, 18));
        Livro livro = new Livro();

        autorRepository.save(autor);

        livro.setIsbn("6845098-89903");
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setPreco(BigDecimal.valueOf(150));
        livro.setData_publicada(LocalDate.of(2019, 10, 27));
        livro.setTitulo("see Reality!");
        livro.setAutor(autor);

        //Com o cascade o auto
        // vai ser salvo junto, mas isso não é muito utilizado em projetos profissionais
        // autorRepository.save(autorId);


        livroRepository.save(livro);

        if(autor.getNome().equals("Vincy")){
            throw new RuntimeException("rollback");
        }

    }


}
