package com.kayo.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;
    @Column
    private String login;
    @Column
    private String senha;
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "roles",columnDefinition = "varchar[]")
    private List<String> roles;
}
