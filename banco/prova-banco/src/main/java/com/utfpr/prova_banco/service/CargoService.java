package com.utfpr.prova_banco.service;

import com.utfpr.prova_banco.entity.Cargo;
import com.utfpr.prova_banco.entity.Funcionario;

import com.utfpr.prova_banco.repository.CargoRepository;
import com.utfpr.prova_banco.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    public void deleteCargo(Integer id) {
        if (!cargoRepository.existsById(id)) {
            throw new IllegalArgumentException("Cargo com id " + id + " não existe.");
        }
        cargoRepository.deleteById(id);
    }

    public Cargo salvarCargo(Cargo cargo) {
    return cargoRepository.save(cargo);}

    @Transactional
    public Cargo salvarCargoComFuncionarios(Cargo cargo, List<Funcionario> funcionarios) {
        Cargo salvo = cargoRepository.save(cargo);
        if (funcionarios != null && !funcionarios.isEmpty()) {
            for (Funcionario f : funcionarios) {
                f.setCargo(salvo);
            }
            funcionarioRepository.saveAll(funcionarios);
        }
        return salvo;
    }

    public List<Cargo> listarCargos() {
        return cargoRepository.findAll();
    }
}



