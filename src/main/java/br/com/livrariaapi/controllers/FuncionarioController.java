package br.com.livrariaapi.controllers;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.livrariaapi.entities.Funcionario;
import br.com.livrariaapi.repositories.IFuncionarioRepository;
import br.com.livrariaapi.requests.FuncionarioPostRequest;
import br.com.livrariaapi.requests.FuncionarioPutRequest;
import io.swagger.annotations.ApiOperation;


@Transactional
@Controller
public class FuncionarioController {

	private static final String ENDPOINT = "/api/funcionario";

	@Autowired
	IFuncionarioRepository funcionarioRepository;

	@ApiOperation("Metodo para realizar cadastro de Funcionario")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody FuncionarioPostRequest request) {
		try {

			if (funcionarioRepository.findByCpf(request.getCpf()) != null
					|| funcionarioRepository.findByEmail(request.getEmail()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Cpf Ou Email ja cadastrado, Porfavor tente outro");
			}

			Funcionario funcionario = new Funcionario();
			funcionario.setNome(request.getNome());
			funcionario.setEmail(request.getEmail());
			funcionario.setCpf(request.getCpf());
	

			funcionarioRepository.save(funcionario);

			return ResponseEntity.status(HttpStatus.OK).body("Funcionario cadastrado com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@ApiOperation("Metodo para realizar cadastro de Funcionario")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody FuncionarioPutRequest request) {

		try {
			Optional<Funcionario> consult = funcionarioRepository.findById(request.getIdFuncionario());

			if (consult.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Não foi possivel alterar Funcionario, porfavor verifique o ID informado");
			}

			Funcionario funcionario = new Funcionario();
			funcionario.setIdFuncionario(request.getIdFuncionario());
			funcionario.setNome(request.getNome());
			funcionario.setEmail(request.getEmail());
			funcionario.setCpf(request.getCpf());
			funcionarioRepository.save(funcionario);

			return ResponseEntity.status(HttpStatus.CREATED).body("Funcionario atualizado com sucesso!");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("Metodo para realizar cadastro de Funcionario")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	public ResponseEntity<Funcionario> getAll() {
		Funcionario funcionario = null;

		try {
			List<Funcionario> funcionarios = (List<Funcionario>) funcionarioRepository.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(funcionario);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation("Metodo para realizar cadastro de Funcionario")
	@RequestMapping(value = ENDPOINT + "{idFuncionario}", method = RequestMethod.GET)
	public ResponseEntity<Funcionario> getById(@PathVariable("idFuncionario") Long idFuncionario) {
		Funcionario funcionario = null;

		try {
			Optional<Funcionario> consult = funcionarioRepository.findById(idFuncionario);

			if (consult.isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

			funcionario = consult.get();

			return ResponseEntity.status(HttpStatus.OK).body(funcionario);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@ApiOperation("Metodo para realizar cadastro de Funcionario")
	@RequestMapping(value = ENDPOINT + "{idFuncionario}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idFuncionario") Long idFuncionario) {
		try {
			
			Optional<Funcionario> consult = funcionarioRepository.findById(idFuncionario);

			if (consult.isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possivel deletar Funcionario");

			Funcionario funcionario = new Funcionario();
			
			funcionarioRepository.delete(funcionario);
			
			return ResponseEntity.status(HttpStatus.OK).body("funcionario deletado com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		
	}

}
