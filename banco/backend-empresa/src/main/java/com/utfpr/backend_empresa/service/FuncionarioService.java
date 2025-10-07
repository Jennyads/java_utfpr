package com.utfpr.backend_empresa.service;

import com.utfpr.backend_empresa.entity.Funcionario;
import com.utfpr.backend_empresa.repository.DepartamentoRepository;
import com.utfpr.backend_empresa.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private DepartamentoService departamentoService;

    //Atividade JPA - Manipular Dados e Transações
    public void aumentarSalarios(Integer percentual) {
        repository.procAumentaSalarios(percentual);
    }

    public List<Funcionario> buscarFuncionariosSemDependentesPorDepartamento(String nomeDepartamento) {
        return repository.findSemDependentesPorDepartamento(nomeDepartamento);
    }

    @Transactional
    public int transferirFuncionarios(Long antigoDeptoId, Long novoDeptoId) {
        return repository.atualizarDepartamentoPorId(antigoDeptoId, novoDeptoId);
    }

    @Transactional
    public int excluirFuncionariosPorDepartamento(Long deptoId) {
        return repository.deleteByDepartamentoId(deptoId);
    }
    public List<Funcionario> buscarPorDepartamentoId(Long codigoDepto) {
        return repository.buscarPorDepartamentoId(codigoDepto);
    }


    //Atividade JPA - Consultas

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


    public List <Funcionario> buscarTodosFuncionarios() {
        return repository.findAll();

    }


}

