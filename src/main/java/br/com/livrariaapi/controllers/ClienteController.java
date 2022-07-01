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

import br.com.livrariaapi.entities.Cliente;
import br.com.livrariaapi.repositories.IClienteRepository;
import br.com.livrariaapi.requests.ClientePostRequest;
import br.com.livrariaapi.requests.ClientePutRequest;
import br.com.livrariaapi.response.ClienteGetResponse;
import io.swagger.annotations.ApiOperation;


@Transactional
@Controller
public class ClienteController {

	private static final String ENDPOINT = "/api/cliente";

	@Autowired
	IClienteRepository clienteRepository;

	
	@ApiOperation("Metodo para realizar cadastro de cliente")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody ClientePostRequest request) {

		try {

			if (clienteRepository.findByCpf(request.getCpf()) != null
					|| clienteRepository.findByEmail(request.getEmail()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Cpf Ou Email ja cadastrado, Porfavor tente outro");
			}

			Cliente cliente = new Cliente();
			cliente.setNome(request.getNome().trim());
			cliente.setEmail(request.getEmail().trim());
			cliente.setCpf(request.getCpf().trim());
		

			clienteRepository.save(cliente);

			return ResponseEntity.status(HttpStatus.CREATED).body("Cliente Cadastrado com sucesso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("metodo para realizar cadastro de Funcionario")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody ClientePutRequest request) {

		try {
			Optional<Cliente> consult = clienteRepository.findById(request.getIdCliente());

			if (consult.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Não foi possivel alterar cliente, porfavor verifique o ID informado");
			}

			Cliente cliente = new Cliente();
			cliente.setIdCliente(request.getIdCliente());
			cliente.setNome(request.getNome().trim());
			cliente.setEmail(request.getEmail().trim());
			cliente.setCpf(request.getCpf().trim());
			

			clienteRepository.save(cliente);

			return ResponseEntity.status(HttpStatus.CREATED).body("Cliente atualizado com sucesso!");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("metodo para deletar Cliente")
	@RequestMapping(value = ENDPOINT + "{idCliente}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idCliente") Long idCliente) {

		try {

			Optional<Cliente> consult = clienteRepository.findById(idCliente);

			if (consult.isPresent()) {
				Cliente cliente = consult.get();
				clienteRepository.delete(cliente);
				return ResponseEntity.status(HttpStatus.OK).body("Cliente Deletado com sucesso com sucesso!");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possivel atualizar Cliente!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("metodo para buscar um cliente pelo id")
	@RequestMapping(value = ENDPOINT + "{idCliente}", method = RequestMethod.GET)
	public ResponseEntity<ClienteGetResponse> getById(@PathVariable("idCliente") Long idCliente) {
		Cliente cliente = null;

		try {
			Optional<Cliente> consult = clienteRepository.findById(idCliente);

			if (consult.isPresent()) {
				cliente = consult.get();
				ClienteGetResponse clienteResponse = new ClienteGetResponse();
				clienteResponse.setIdCliente(cliente.getIdCliente());
				clienteResponse.setNomeString(cliente.getNome());
				clienteResponse.setEmail(cliente.getEmail());
				clienteResponse.setCpf(cliente.getCpf());
				return ResponseEntity.status(HttpStatus.OK).body(clienteResponse);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		
	}

	@ApiOperation("metodo para Fazer a busca completa dos Clientes")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	public ResponseEntity<List<ClienteGetResponse>> getAll() {

		try {
			List<Cliente> clientes = (List<Cliente>) clienteRepository.findAll();
			
			if(clientes.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			
			List<ClienteGetResponse> listaClientes = new ArrayList<ClienteGetResponse>();
			ClienteGetResponse clienteGetResponse;
			for(Cliente cliente: clientes) {
				clienteGetResponse = new ClienteGetResponse();
				clienteGetResponse.setIdCliente(cliente.getIdCliente());
				clienteGetResponse.setNomeString(cliente.getNome());
				clienteGetResponse.setEmail(cliente.getEmail());
				clienteGetResponse.setCpf(cliente.getCpf());
				listaClientes.add(clienteGetResponse);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(listaClientes);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

}
