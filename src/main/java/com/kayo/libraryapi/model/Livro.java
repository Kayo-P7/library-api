package com.kayo.libraryapi.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
@ToString(exclude = "autor")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "isnb", length = 20, nullable = false)
    private String isnb;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate data_publicada;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", nullable = false, precision = 18, scale = 2) //18,00
    private BigDecimal preco;

    @ManyToOne(fetch = FetchType.LAZY)
            //(fetch = FetchType.EAGER) // esse é o padrão
         //   (fetch = FetchType.LAZY)caso você queira trazer só os dados do autor
    //(cascade = CascadeType.ALL)// um autor pode ter um ou mais livros. Especificando o tipo de relacionamento


    @JoinColumn(name = "id_autor") //coluna de relacionamento
    private Autor autor; //A coluna livro que controla o relacionamento




}
