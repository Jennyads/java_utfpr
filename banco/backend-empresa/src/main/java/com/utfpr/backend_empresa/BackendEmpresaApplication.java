package com.utfpr.backend_empresa;

import com.utfpr.backend_empresa.entity.Funcionario;
import com.utfpr.backend_empresa.service.DepartamentoService;
import com.utfpr.backend_empresa.service.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendEmpresaApplication {

	private static final Logger log = LoggerFactory.getLogger(BackendEmpresaApplication.class);

	@Autowired
	FuncionarioService funcionarioService;

	@Autowired
	DepartamentoService departamentoService;

	public static void main(String[] args) {
		SpringApplication.run(BackendEmpresaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(DepartamentoService departamentoService, FuncionarioService funcionarioService) {
		return (args) -> {

			// Consulta 1: Buscar funcionário por nome + quantidade de dependentes
			String nomeBuscado = "Daniel";
			int qtdDependentesBuscado = 3;

			log.info("==============================================================");
			log.info("🔎 Consulta 1: Buscar funcionário por nome + quantidade de dependentes");
			log.info("==============================================================");

			var funcionarios1 = funcionarioService.buscarFuncionarioPorNomeEQuantidadeDependentes(nomeBuscado, qtdDependentesBuscado);

			if (funcionarios1.isEmpty()) {
				log.info("Nenhum funcionário encontrado com o nome '{}' e {} dependente(s).", nomeBuscado, qtdDependentesBuscado);
			} else {
				for (Funcionario f : funcionarios1) {
					log.info("👤 Funcionário:");
					log.info("    ▸ Nome: {}", f.getNomeFuncionario());
					log.info("    ▸ Dependentes: {}", f.getQtdDependentesFuncionario());
					log.info("    ▸ Salário: R$ {}", f.getSalarioFuncionario());
					log.info("    ▸ Cargo: {}", f.getCargoFuncionario());
					log.info("    ▸ Departamento: {}", f.getDepartamento().getNomeCategoria());
				}
			}

			// Consulta 2: Listar funcionários do departamento informado
			String departamentoAlvo = "TI";
			log.info("==============================================================");
			log.info("🏢 Consulta 2: Listar funcionários do departamento '{}'", departamentoAlvo);
			log.info("==============================================================");

			var funcionarios2 = funcionarioService.buscarFuncionariosPorDepartamento(departamentoAlvo);

			if (funcionarios2.isEmpty()) {
				log.info("Nenhum funcionário encontrado no departamento '{}'.", departamentoAlvo);
			} else {
				for (Funcionario f : funcionarios2) {
					log.info("👤 Funcionário:");
					log.info("    ▸ Nome: {}", f.getNomeFuncionario());
					log.info("    ▸ Cargo: {}", f.getCargoFuncionario());
					log.info("    ▸ Salário: R$ {}", f.getSalarioFuncionario());
					log.info("    ▸ Departamento: {}", f.getDepartamento().getNomeCategoria());
				}
			}

			// Consulta 3: Listar o primeiro departamento cadastrado
			log.info("==============================================================");
			log.info("🏢 Consulta 3: Listar o primeiro departamento cadastrado");
			log.info("==============================================================");

			var departamento = departamentoService.buscarPrimeiroDepartamento();

			if (departamento == null) {
				log.info("Nenhum departamento cadastrado encontrado.");
			} else {
				log.info("🏢 Departamento:");
				log.info("    ▸ Código: {}", departamento.getId());
				log.info("    ▸ Nome: {}", departamento.getNomeCategoria());
			}

			// Consulta 4: Listar o funcionário com o maior salário
			log.info("==============================================================");
			log.info("💰 Consulta 4: Listar o funcionário com o maior salário");
			log.info("==============================================================");

			var funcionario = funcionarioService.buscarFuncionarioComMaiorSalario();

			if (funcionario == null) {
				log.info("Nenhum funcionário encontrado.");
			} else {
				log.info("👤 Funcionário com maior salário:");
				log.info("    ▸ Nome: {}", funcionario.getNomeFuncionario());
				log.info("    ▸ Cargo: {}", funcionario.getCargoFuncionario());
				log.info("    ▸ Salário: R$ {}", funcionario.getSalarioFuncionario());
				log.info("    ▸ Departamento: {}", funcionario.getDepartamento().getNomeCategoria());
			}

			// Consulta 5: Top 3 salários
			log.info("==============================================================");
			log.info("🏆 Consulta 5: Listar os 3 funcionários com os maiores salários");
			log.info("==============================================================");

			var top3 = funcionarioService.buscarTop3FuncionariosComMaioresSalarios();

			if (top3.isEmpty()) {
				log.info("Nenhum funcionário encontrado.");
			} else {
				int posicao = 1;
				for (Funcionario f : top3) {
					log.info("👤 Funcionário {}º:", posicao++);
					log.info("    ▸ Nome: {}", f.getNomeFuncionario());
					log.info("    ▸ Cargo: {}", f.getCargoFuncionario());
					log.info("    ▸ Salário: R$ {}", f.getSalarioFuncionario());
					log.info("    ▸ Departamento: {}", f.getDepartamento().getNomeCategoria());
				}
			}

			// Consulta 6: Sem dependentes
			log.info("==============================================================");
			log.info("👨‍👩‍👧 Consulta 6: Listar funcionários sem dependentes (ordem crescente por nome)");
			log.info("==============================================================");

			var funcionariosSemDependentes = funcionarioService.buscarFuncionariosSemDependentesOrdenadoPorNome();

			if (funcionariosSemDependentes.isEmpty()) {
				log.info("Nenhum funcionário sem dependentes encontrado.");
			} else {
				for (Funcionario f : funcionariosSemDependentes) {
					log.info("👤 Funcionário:");
					log.info("    ▸ Nome: {}", f.getNomeFuncionario());
					log.info("    ▸ Cargo: {}", f.getCargoFuncionario());
					log.info("    ▸ Salário: R$ {}", f.getSalarioFuncionario());
					log.info("    ▸ Departamento: {}", f.getDepartamento().getNomeCategoria());
				}
			}

			// Consulta 7: Salário maior que valor
			double salarioMinimo = 15000.00;

			log.info("==============================================================");
			log.info("💵 Consulta 7: Funcionários com salário maior que R$ {}", salarioMinimo);
			log.info("==============================================================");

			var funcionariosComSalarioMaior = funcionarioService.buscarPorSalarioMaiorQue(salarioMinimo);

			if (funcionariosComSalarioMaior.isEmpty()) {
				log.info("Nenhum funcionário encontrado com salário maior que R$ {}", salarioMinimo);
			} else {
				for (Funcionario f : funcionariosComSalarioMaior) {
					log.info("👤 Funcionário:");
					log.info("    ▸ Nome: {}", f.getNomeFuncionario());
					log.info("    ▸ Cargo: {}", f.getCargoFuncionario());
					log.info("    ▸ Salário: R$ {}", f.getSalarioFuncionario());
					log.info("    ▸ Departamento: {}", f.getDepartamento().getNomeCategoria());
				}
			}

			// Consulta 8: Salário maior que valor (native query)
			double salarioMinimoNativo = 15000.00;

			log.info("==============================================================");
			log.info("🛠️ Consulta 8: Funcionários com salário > R$ {} (native query)", salarioMinimoNativo);
			log.info("==============================================================");

			var funcionariosNativo = funcionarioService.buscarPorSalarioMaiorQueNativo(salarioMinimoNativo);

			if (funcionariosNativo.isEmpty()) {
				log.info("Nenhum funcionário encontrado com salário maior que R$ {} (native).", salarioMinimoNativo);
			} else {
				for (Funcionario f : funcionariosNativo) {
					log.info("👤 Funcionário:");
					log.info("    ▸ Nome: {}", f.getNomeFuncionario());
					log.info("    ▸ Cargo: {}", f.getCargoFuncionario());
					log.info("    ▸ Salário: R$ {}", f.getSalarioFuncionario());
					log.info("    ▸ Departamento: {}", f.getDepartamento().getNomeCategoria());
				}
			}

			// Consulta 11.1: Buscar funcionários por quantidade de dependentes usando @NamedQuery
			log.info("==============================================================");
			int qtdDependentes = 3;
			log.info("👶 Consulta 11.1: Listar funcionários com {} dependentes", qtdDependentes);
			log.info("==============================================================");

			var funcionariosQtdDep = funcionarioService.buscarPorQtdDependentesNamed(qtdDependentes);

			if (funcionariosQtdDep.isEmpty()) {
				log.info("Nenhum funcionário encontrado com {} dependentes.", qtdDependentesBuscado);
			} else {
				for (Funcionario f : funcionariosQtdDep) {
					log.info("👤 Funcionário:");
					log.info("    ▸ Nome: {}", f.getNomeFuncionario());
					log.info("    ▸ Cargo: {}", f.getCargoFuncionario());
					log.info("    ▸ Salário: R$ {}", f.getSalarioFuncionario());
					log.info("    ▸ Dependentes: {}", f.getQtdDependentesFuncionario());
					log.info("    ▸ Departamento: {}", f.getDepartamento().getNomeCategoria());
				}
			}


			// Consulta 11.2: Buscar funcionários com nome parcial usando @NamedNativeQuery
			log.info("==============================================================");
			String nomeParcial = "Ramos";
			log.info("🔠 Consulta 11.2: Listar funcionários com nome contendo {}", nomeParcial);
			log.info("==============================================================");



			var funcionariosNomeParcial = funcionarioService.buscarPorNomeParcialNamedNative(nomeParcial);

			if (funcionariosNomeParcial.isEmpty()) {
				log.info("Nenhum funcionário encontrado com nome contendo {}.", nomeParcial);
			} else {
				for (Funcionario f : funcionariosNomeParcial) {
					log.info("👤 Funcionário:");
					log.info("    ▸ Nome: {}", f.getNomeFuncionario());
					log.info("    ▸ Cargo: {}", f.getCargoFuncionario());
					log.info("    ▸ Salário: R$ {}", f.getSalarioFuncionario());
					log.info("    ▸ Departamento: {}", f.getDepartamento().getNomeCategoria());
				}
			}

		};
	}
}
