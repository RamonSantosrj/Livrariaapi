package br.com.livrariaapi.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.livrariaapi.Helpers.CalcularVendaHelper;
import br.com.livrariaapi.Helpers.FindEmailTokenHelper;
import br.com.livrariaapi.entities.Cliente;
import br.com.livrariaapi.entities.Funcionario;
import br.com.livrariaapi.entities.Livro;
import br.com.livrariaapi.entities.Venda;
import br.com.livrariaapi.repositories.IClienteRepository;
import br.com.livrariaapi.repositories.IFuncionarioRepository;
import br.com.livrariaapi.repositories.ILivroRepository;
import br.com.livrariaapi.repositories.IVendaRepository;
import br.com.livrariaapi.requests.LivroPostRequest;
import br.com.livrariaapi.requests.LivroVendaPostRequest;
import br.com.livrariaapi.requests.VendaPostRequest;
import br.com.livrariaapi.response.VendaGetResponse;
import io.swagger.annotations.ApiOperation;

@Transactional
@Controller
public class VendaController {

	private static final String ENDPOINT = "/api/venda";

	@Autowired
	IVendaRepository vendaRepository;
	@Autowired
	IClienteRepository clienteRepository;
	@Autowired
	IFuncionarioRepository funcionarioRepository;
	@Autowired
	ILivroRepository livroRepository;

	@ApiOperation("metodo para cadastro da venda")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody VendaPostRequest request) {
		try {
			if (request.getClientePostRequest() == null)
				throw new IllegalArgumentException("nao foi possivel encontrar o cliente");

			if (request.getFuncionarioPostRequest() == null)
				throw new IllegalArgumentException("nao foi possivel Encontrar o Funcionario");

			if (request.getListLivro().isEmpty())
				throw new IllegalArgumentException("nao foi possivel encontrar o Livro");

			List<Livro> livros = new ArrayList<Livro>();

			Livro livro1;
			for (LivroVendaPostRequest livro : request.getListLivro()) {
				livro1 = new Livro();
				livro1.setAutor(livro.getAutor().trim());
				livro1.setIdLivro(livro.getIdLivro());
				livro1.setNome(livro.getNome().trim());
				livro1.setPreco(livro.getPreco());
				livros.add(livro1);
			}

			Cliente cliente = new Cliente();
			cliente.setCpf(request.getClientePostRequest().getCpf().trim());
			cliente.setEmail(request.getClientePostRequest().getEmail().trim());
			cliente.setNome(request.getClientePostRequest().getNome().trim());
			cliente.setIdCliente(request.getClientePostRequest().getIdCliente());

			Funcionario funcionario = new Funcionario();
			funcionario.setCpf(request.getFuncionarioPostRequest().getCpf().trim());
			funcionario.setEmail(request.getFuncionarioPostRequest().getEmail().trim());
			funcionario.setNome(request.getFuncionarioPostRequest().getNome().trim());
			funcionario.setIdFuncionario(request.getFuncionarioPostRequest().getIdFuncionario());

			Date dataHora = new SimpleDateFormat("dd/MM/yyyy-HH:mm")
					.parse(request.getData().trim() + "-" + request.getHora().trim());

			Venda venda = new Venda();

			venda.setDataHora(dataHora);
			venda.setCliente(cliente);
			venda.setFuncionario(funcionario);
			venda.setLivros(livros);
			venda.setPreco(CalcularVendaHelper.CalcularVenda(livros));

			vendaRepository.save(venda);

			return ResponseEntity.status(HttpStatus.OK).body("cadastrado com sucesso");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("metodo para retorna as vendas feitas do cliente")
	@RequestMapping(value = ENDPOINT + "{cpf}", method = RequestMethod.GET)
	ResponseEntity<List<VendaGetResponse>> get(@PathVariable("cpf") String cpf,HttpServletResponse request) {

		try {
			Cliente cliente = clienteRepository.findByCpf(cpf.trim());

			List<VendaGetResponse> lista = new ArrayList<VendaGetResponse>();
			VendaGetResponse vendaGetResponse;
			for (Venda venda : vendaRepository.findbyCliente(cliente.getIdCliente())) {

				vendaGetResponse = new VendaGetResponse();
				vendaGetResponse.setIdVenda(venda.getIdVenda());
				vendaGetResponse.setNomeCliente(venda.getCliente().getNome());
				vendaGetResponse.setEmailCliente(venda.getCliente().getEmail());
				vendaGetResponse.setNomeFuncionario(venda.getFuncionario().getNome());
				vendaGetResponse.setEmailFuncionario(venda.getFuncionario().getEmail());
				vendaGetResponse.setDataHora(new SimpleDateFormat("dd/MM/yyyy HH:MM").format(venda.getDataHora()));
				vendaGetResponse.setPrecoVenda(venda.getPreco());
//				vendaGetResponse.setLivros(venda.getLivros());

				lista.add(vendaGetResponse);
			}

			return ResponseEntity.status(HttpStatus.OK).body(lista);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
