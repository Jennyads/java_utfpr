package com.utfpr.prova_banco.service;


import com.utfpr.prova_banco.entity.Cargo;
import com.utfpr.prova_banco.entity.Funcionario;
import com.utfpr.prova_banco.repository.FuncionarioRepository;
import com.utfpr.prova_banco.repository.CargoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public long contarFuncionarios() {
        return funcionarioRepository.count();
    }

    @Autowired
    private CargoRepository cargoRepository;

    public Funcionario salvarFuncionario(Funcionario funcionario) {
    if (funcionario.getCargo() == null || funcionario.getCargo().getId() == null) {
        throw new IllegalArgumentException("Cargo é obrigatório para salvar o funcionário.");
    }

    Integer cargoId = funcionario.getCargo().getId();
    Cargo cargo = cargoRepository.findById(cargoId)
            .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado (id=" + cargoId + ")"));

    funcionario.setCargo(cargo);
    return funcionarioRepository.save(funcionario);
    }

    public Funcionario salvarFuncionarioComCargo(Funcionario funcionario, Integer cargoId) {
        Cargo cargo = cargoRepository.findById(cargoId)
                .orElseThrow(() -> new EntityNotFoundException("Cargo não encontrado (id=" + cargoId + ")"));
        funcionario.setCargo(cargo);
        return funcionarioRepository.save(funcionario);
    }

    public void deleteFuncionario(Integer id) {
    if (!funcionarioRepository.existsById(id)) {
        throw new IllegalArgumentException("Funcionário com id " + id + " não existe.");
    }
    funcionarioRepository.deleteById(id);}

    public List<Funcionario> listarTodosOrdenadosPorNome() {
    return funcionarioRepository.findAllByOrderByNomeAsc();
    }

    public List<Funcionario> listarTodos() {return funcionarioRepository.findAll();}

}







