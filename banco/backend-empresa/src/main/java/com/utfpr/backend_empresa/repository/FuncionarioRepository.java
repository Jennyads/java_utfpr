package com.utfpr.backend_empresa.repository;

import com.utfpr.backend_empresa.entity.Departamento;
import com.utfpr.backend_empresa.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByNomeFuncionarioContainingAndQtdDependentesFuncionario(String nomeFuncionario, Integer qtdDependentesFuncionario);

    @Query("SELECT f FROM Funcionario f WHERE f.departamento.nomeCategoria = :nomeDepartamento")
    List<Funcionario> listarPorDepartamento(@Param("nomeDepartamento") String nomeDepartamento);


    Optional<Funcionario> findTopByOrderBySalarioFuncionarioDesc();

    List<Funcionario> findTop3ByOrderBySalarioFuncionarioDesc();

    @Query("SELECT f FROM Funcionario f WHERE f.qtdDependentesFuncionario = 0 ORDER BY f.nomeFuncionario ASC")
    List<Funcionario> buscarFuncionariosSemDependentesOrdenadoPorNome();

    @Query("SELECT f FROM Funcionario f WHERE f.salarioFuncionario > :valor")
    List<Funcionario> buscarPorSalarioMaiorQue(@Param("valor") Double valor);

    @Query(value = "SELECT * FROM funcionarios WHERE salario > :valor", nativeQuery = true)
    List<Funcionario> buscarPorSalarioMaiorQueNativo(@Param("valor") Double valor);

    //questão 11
    @Query(name = "Funcionario.findByQtdDependentes")
    List<Funcionario> buscarPorQtdDependentesNamed(@Param("qtd") int qtd);

    @Query(name = "Funcionario.findByNomeParcial", nativeQuery = true)
    List<Funcionario> buscarPorNomeParcialNamedNative(@Param("nome") String nome);










}
