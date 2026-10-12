package com.kayo.libraryapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "autor", schema = "public")
@Getter
@Setter
@ToString(exclude = "livros")
@Entity
@EntityListeners(AuditingEntityListener.class)/*
toda as vezes que eu fizer uma operação na entidade ele vai fica escutando
e configurando ->
    @CreatedDate //ele já coloca a data automaticamente
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @LastModifiedDate // modifica sozinho e atualiza
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
*/
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID) //gera o código sozinho Igual ao --> SERIAL OU AUTO_INCREMENT
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    // nullable = false significa que o campo não pode ficar vazio
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;
    @LastModifiedDate
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;


    @Column(name = "id_usuario")
    private UUID idUsuario;

    @CreatedDate //ele já coloca a data automaticamente
    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL) // um autorId para muitos livros
    // mappedBy aponta para o ATRIBUTO "autorId" que está dentro da classe Livro
    private List<Livro> livros = new ArrayList<>();


    @Deprecated
    public Autor() {
        //para o uso do framework
    }
}
