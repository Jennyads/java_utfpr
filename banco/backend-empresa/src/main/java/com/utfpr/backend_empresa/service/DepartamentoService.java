package com.utfpr.backend_empresa.service;

import com.utfpr.backend_empresa.entity.Departamento;
import com.utfpr.backend_empresa.entity.Funcionario;
import com.utfpr.backend_empresa.repository.DepartamentoRepository;
import com.utfpr.backend_empresa.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository,
                               FuncionarioRepository funcionarioRepository) {
        this.departamentoRepository = departamentoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }
    //Atividade JPA - Manipular Dados e Transações

    @Transactional
    public Funcionario criarDeptoEAssociarFuncionarioExistente(String nomeDepto, Long funcionarioId) {
        var depto = new Departamento();
        depto.setNomeCategoria(nomeDepto);
        depto = departamentoRepository.save(depto);


        var func = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado: " + funcionarioId));
        func.setDepartamento(depto);


        return funcionarioRepository.save(func);
    }


    @Transactional
    public Funcionario criarDeptoECriarFuncionario(String nomeDepto,
                                                   String nomeFuncionario,
                                                   Integer qtdDependentes,
                                                   Double salario,
                                                   String cargo) {
        var depto = new Departamento();
        depto.setNomeCategoria(nomeDepto);
        depto = departamentoRepository.save(depto);

        var func = new Funcionario();
        func.setNomeFuncionario(nomeFuncionario);
        func.setQtdDependentesFuncionario(qtdDependentes);
        func.setSalarioFuncionario(salario);
        func.setCargoFuncionario(cargo);
        func.setDepartamento(depto);

        return funcionarioRepository.save(func);
    }

    //Atividade JPA - Consultas

    public Departamento buscarPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departamento não encontrado: " + id));
    }

    public Departamento buscarPrimeiroDepartamento() {
        return departamentoRepository.findFirstByOrderByIdAsc().orElse(null);
    }



}
