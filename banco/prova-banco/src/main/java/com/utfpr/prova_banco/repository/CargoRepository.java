package com.utfpr.prova_banco.repository;

import com.utfpr.prova_banco.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {}
