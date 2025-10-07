package com.utfpr.prova_banco.repository;

import com.utfpr.prova_banco.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    long count();
    List<Funcionario> findAllByOrderByNomeAsc();


}

