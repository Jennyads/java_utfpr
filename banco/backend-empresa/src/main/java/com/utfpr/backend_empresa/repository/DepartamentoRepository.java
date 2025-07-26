package com.utfpr.backend_empresa.repository;

import com.utfpr.backend_empresa.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    Optional<Departamento> findFirstByOrderByIdAsc();


}
