package com.utfpr.backend_empresa.service;

import com.utfpr.backend_empresa.entity.Funcionario;
import com.utfpr.backend_empresa.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public List<Funcionario> buscarFuncionarioPorNomeEQuantidadeDependentes(String nome, int qtdDependentes) {
        return repository.findByNomeFuncionarioContainingAndQtdDependentesFuncionario(nome, qtdDependentes);
    }

    public List<Funcionario> buscarFuncionariosPorDepartamento(String nomeDepartamento) {
        return repository.listarPorDepartamento(nomeDepartamento);
    }

    public Funcionario buscarFuncionarioComMaiorSalario() {
        return repository.findTopByOrderBySalarioFuncionarioDesc().orElse(null);
    }

    public List<Funcionario> buscarTop3FuncionariosComMaioresSalarios() {
        return repository.findTop3ByOrderBySalarioFuncionarioDesc();
    }

    public List<Funcionario> buscarFuncionariosSemDependentesOrdenadoPorNome() {
        return repository.buscarFuncionariosSemDependentesOrdenadoPorNome();
    }

    public List<Funcionario> buscarPorSalarioMaiorQue(Double valor) {
        return repository.buscarPorSalarioMaiorQue(valor);
    }

    public List<Funcionario> buscarPorSalarioMaiorQueNativo(Double valor) {
        return repository.buscarPorSalarioMaiorQueNativo(valor);
    }

    public List<Funcionario> buscarPorQtdDependentesNamed(int qtd) {
        return repository.buscarPorQtdDependentesNamed(qtd);
    }

    public List<Funcionario> buscarPorNomeParcialNamedNative(String nome) {
        return repository.buscarPorNomeParcialNamedNative(nome);
    }










}

