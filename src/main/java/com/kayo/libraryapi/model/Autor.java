package com.kayo.libraryapi.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Table(name = "autor", schema = "public")
@Getter
@Setter
@ToString(exclude = "livros")
@Entity
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID) //gera o código sozinho Igual ao --> SERIAL OU AUTO_INCREMENT
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    // nullable = false significa que o campo não pode ficar vazio
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate data_nascimento;
    
    @Column(name = "nascionalidade", length = 50, nullable = false)
    private String nascionalidade;


    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL) // um autor para muitos livros
    // mappedBy aponta para o ATRIBUTO "autor" que está dentro da classe Livro

    private List<Livro> livros = new ArrayList<>();



    @Deprecated
    public Autor() {
    //para o uso do framework
    }
}
