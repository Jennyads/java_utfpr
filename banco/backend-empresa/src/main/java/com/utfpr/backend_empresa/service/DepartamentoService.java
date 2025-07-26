package com.utfpr.backend_empresa.service;

import com.utfpr.backend_empresa.entity.Departamento;
import com.utfpr.backend_empresa.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoService {

    @Autowired
    DepartamentoRepository departamentoRepository;

    public Departamento buscarPrimeiroDepartamento() {
        return departamentoRepository.findFirstByOrderByIdAsc().orElse(null);
    }
}
