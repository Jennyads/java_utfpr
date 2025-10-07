package com.utfpr.prova_banco;

import com.utfpr.prova_banco.entity.Funcionario;
import com.utfpr.prova_banco.service.CargoService;
import com.utfpr.prova_banco.service.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProvaBancoApplication {

	private static final Logger log = LoggerFactory.getLogger(ProvaBancoApplication.class);

	@Autowired
	FuncionarioService funcionarioService;

	@Autowired
	CargoService cargoServiceService;


	public static void main(String[] args) {
		SpringApplication.run(ProvaBancoApplication.class, args);



	}

	@Bean
	public CommandLineRunner demo(CargoService cargoService, FuncionarioService funcionarioService) {
		return (args) -> {

			//Inserir pelo menos 3 Cargos;
			var cargoDev = new com.utfpr.prova_banco.entity.Cargo();
			cargoDev.setNome("Desenvolvedor");
			cargoDev = cargoService.salvarCargo(cargoDev);
			log.info("Cargo salvo: {} (id={})", cargoDev.getNome(), cargoDev.getId());

			var cargoQa = new com.utfpr.prova_banco.entity.Cargo();
			cargoQa.setNome("QA");
			cargoQa = cargoService.salvarCargo(cargoQa);
			log.info("Cargo salvo: {} (id={})", cargoQa.getNome(), cargoQa.getId());

			var cargoRh = new com.utfpr.prova_banco.entity.Cargo();
			cargoRh.setNome("RH");
			cargoRh = cargoService.salvarCargo(cargoRh);
			log.info("Cargo salvo: {} (id={})", cargoRh.getNome(), cargoRh.getId());

			//Inserir pelo menos 3 Funcionários;
			var f1 = new Funcionario();
			f1.setNome("Lucas Ramos");
			f1.setSexo("M");
			f1.setTelefone("11-90000-0001");
			f1.setCargo(cargoDev);
			f1 = funcionarioService.salvarFuncionario(f1);
			log.info("Funcionário salvo: {} (id={}) cargo={}", f1.getNome(), f1.getId(), f1.getCargo().getNome());

			var f2 = new Funcionario();
			f2.setNome("Jeniffer Pereira");
			f2.setSexo("F");
			f2.setTelefone("11-90000-0002");
			f2.setCargo(cargoQa);
			f2 = funcionarioService.salvarFuncionario(f2);
			log.info("Funcionário salvo: {} (id={}) cargo={}", f2.getNome(), f2.getId(), f2.getCargo().getNome());

			var f3 = new Funcionario();
			f3.setNome("Cesar Yona");
			f3.setSexo("M");
			f3.setTelefone("11-90000-0003");
			f3.setCargo(cargoDev);
			f3 = funcionarioService.salvarFuncionario(f3);
			log.info("Funcionário salvo: {} (id={}) cargo={}", f3.getNome(), f3.getId(), f3.getCargo().getNome());


			//Excluir pelo menos 1 Cargo pelo identificador (*1);
			var cargoTemp = new com.utfpr.prova_banco.entity.Cargo();
			cargoTemp.setNome("Cargo Temporário");
			cargoTemp = cargoService.salvarCargo(cargoTemp);
			Integer cargoTempId = cargoTemp.getId();
			log.info("Cargo temporário salvo para exclusão: {} (id={})", cargoTemp.getNome(), cargoTempId);

			cargoService.deleteCargo(cargoTempId);
			log.info("Cargo id={} excluído com sucesso", cargoTempId);

			//Excluir pelo menos 1 Funcionario pelo identificador (*1);
			Integer funcionarioIdParaExcluir = f3.getId();
			funcionarioService.deleteFuncionario(funcionarioIdParaExcluir);
			log.info("Funcionário id={} excluído com sucesso", funcionarioIdParaExcluir);

			//Listar todos os Funcionários;

			log.info("Listando todos os Funcionários");
			funcionarioService.listarTodos()
					.forEach(f -> log.info("Funcionario: id={} nome={} cargo={}",
							f.getId(), f.getNome(), f.getCargo().getNome()));

			//Listar todos os Cargos;
			log.info("Listando todos os Cargos");
			cargoService.listarCargos()
					.forEach(c -> log.info("Cargo: id={} nome={}",
							c.getId(), c.getNome()));


			//Listar os Funcionários em Ordem Alfabética;
			log.info("===== Funcionários em Ordem Alfabética =====");
			funcionarioService.listarTodosOrdenadosPorNome()
					.forEach(f -> log.info("Funcionario: id={} nome={} cargo={}",
							f.getId(), f.getNome(), f.getCargo().getNome()));

			//Listar a Quantidade de Funcionários
			long qtd = funcionarioService.contarFuncionarios();
			log.info("===== Quantidade de Funcionários: {} =====", qtd);



		};
	}
}
