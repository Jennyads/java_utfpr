package com.utfpr.backend_empresa.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "funcionarios")
@Data
//questão 9
@NamedQuery(
        name = "Funcionario.findByQtdDependentes",
        query = "SELECT f FROM Funcionario f WHERE f.qtdDependentesFuncionario = :qtd"
)
//questão 10
@NamedNativeQuery(
        name = "Funcionario.findByNomeParcial",
        query = "SELECT * FROM funcionarios WHERE nome LIKE CONCAT('%', :nome, '%')",
        resultClass = Funcionario.class
)

public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false)
    private Long id;

    @Column(name = "nome", length = 100)
    private String nomeFuncionario;

    @Column(name = "qtd_dependentes")
    private Integer qtdDependentesFuncionario;

    @Column(name = "salario")
    private Double salarioFuncionario;

    @Column(name = "cargo", length = 50)
    private String cargoFuncionario;

    @ManyToOne
    @JoinColumn(name = "departamento_codigo", nullable = false)
    private Departamento departamento;
}


