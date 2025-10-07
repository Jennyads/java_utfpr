package com.utfpr.backend_empresa.repository;

import com.utfpr.backend_empresa.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    Optional<Departamento> findFirstByOrderByIdAsc();



}
