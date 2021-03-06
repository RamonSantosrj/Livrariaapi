package br.com.livrariaapi.controllers;

import java.util.ArrayList;
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
import br.com.livrariaapi.response.FuncionarioGetResponse;
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
			funcionario.setNome(request.getNome().trim());
			funcionario.setEmail(request.getEmail().trim());
			funcionario.setCpf(request.getCpf().trim());

			funcionarioRepository.save(funcionario);

			return ResponseEntity.status(HttpStatus.OK).body("Funcionario cadastrado com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@ApiOperation("Metodo para alterar de Funcionario")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody FuncionarioPutRequest request) {

		try {
			Optional<Funcionario> consult = funcionarioRepository.findById(request.getIdFuncionario());

			if (consult.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("N??o foi possivel alterar Funcionario, porfavor verifique o ID informado");
			}

			Funcionario funcionario = new Funcionario();
			funcionario.setIdFuncionario(request.getIdFuncionario());
			funcionario.setNome(request.getNome().trim());
			funcionario.setEmail(request.getEmail().trim());
			funcionario.setCpf(request.getCpf().trim());
			funcionarioRepository.save(funcionario);

			return ResponseEntity.status(HttpStatus.CREATED).body("Funcionario atualizado com sucesso!");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("Metodo para realizar a busca de todos Funcionario")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	public ResponseEntity<List<FuncionarioGetResponse>> getAll() {

		try {
			List<Funcionario> funcionarios = (List<Funcionario>) funcionarioRepository.findAll();

			if (funcionarios.isEmpty())
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

			List<FuncionarioGetResponse> listaFuncionarios = new ArrayList<FuncionarioGetResponse>();
			FuncionarioGetResponse funcionarioResponse;
			for (Funcionario funcionario : funcionarios) {
				funcionarioResponse = new FuncionarioGetResponse();
				funcionarioResponse.setIdFuncionario(funcionario.getIdFuncionario());
				funcionarioResponse.setNomeString(funcionario.getNome());
				funcionarioResponse.setEmail(funcionario.getEmail());
				funcionarioResponse.setCpf(funcionario.getCpf());
				listaFuncionarios.add(funcionarioResponse);
			}

			return ResponseEntity.status(HttpStatus.OK).body(listaFuncionarios);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation("Metodo para buscar pelo id o Funcionario")
	@RequestMapping(value = ENDPOINT + "{idFuncionario}", method = RequestMethod.GET)
	public ResponseEntity<FuncionarioGetResponse> getById(@PathVariable("idFuncionario") Long idFuncionario) {
		Funcionario funcionario = null;

		try {
			Optional<Funcionario> consult = funcionarioRepository.findById(idFuncionario);

			if (consult.isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

			funcionario = consult.get();
			FuncionarioGetResponse funcionarioResponse = new FuncionarioGetResponse();
			funcionarioResponse.setIdFuncionario(funcionario.getIdFuncionario());
			funcionarioResponse.setNomeString(funcionario.getNome());
			funcionarioResponse.setEmail(funcionario.getEmail());
			funcionarioResponse.setCpf(funcionario.getCpf());

			return ResponseEntity.status(HttpStatus.OK).body(funcionarioResponse);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@ApiOperation("Metodo para deletar pelo id o Funcionario")
	@RequestMapping(value = ENDPOINT + "{idFuncionario}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idFuncionario") Long idFuncionario) {
		try {

			Optional<Funcionario> consult = funcionarioRepository.findById(idFuncionario);

			if (consult.isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("N??o foi possivel deletar Funcionario");

			Funcionario funcionario = consult.get();

			funcionarioRepository.delete(funcionario);

			return ResponseEntity.status(HttpStatus.OK).body("funcionario deletado com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

}
