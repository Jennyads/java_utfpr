package com.utfpr.prova_banco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cargo")
@Data

public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_cargo")
    private Integer id;

    @Column(name = "cargo", length = 50, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "cargo")
    private List<Funcionario> funcionarios;

}
