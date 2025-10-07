package com.utfpr.prova_banco.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "funcionario")
@Data

public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_funcionario")
    private Integer id;

    @Column(name = "nome", length = 70, nullable = false)
    private String nome;

    @Column(name = "sexo", length = 10, nullable = false)
    private String sexo;

    @Column(name = "telefone", length = 20, nullable = false)
    private String telefone;


    @ManyToOne
    @JoinColumn(name = "cod_cargo", nullable = false)
    private Cargo cargo;
}
