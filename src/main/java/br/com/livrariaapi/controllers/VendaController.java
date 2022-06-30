package br.com.livrariaapi.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import br.com.livrariaapi.Helpers.CalcularVendaHelper;
import br.com.livrariaapi.entities.Cliente;
import br.com.livrariaapi.entities.Funcionario;
import br.com.livrariaapi.entities.Livro;
import br.com.livrariaapi.entities.Venda;
import br.com.livrariaapi.repositories.IClienteRepository;
import br.com.livrariaapi.repositories.IFuncionarioRepository;
import br.com.livrariaapi.repositories.ILivroRepository;
import br.com.livrariaapi.repositories.IVendaRepository;
import br.com.livrariaapi.requests.VendaPostRequest;
import io.swagger.annotations.ApiOperation;

@Transactional
@Controller
public class VendaController {

	private static final String ENDPOINT ="/api/venda";
	
	@Autowired
	IVendaRepository vendaRepository;
	@Autowired
	IClienteRepository clienteRepository;
	@Autowired
	IFuncionarioRepository funcionarioRepository;
	@Autowired
	ILivroRepository livroRepository;
	
	
	@ApiOperation("metodo para cadastro da venda")
	@RequestMapping(value = ENDPOINT +"{idCliente}" + "&" + "{idFuncionario}" +"&" + "{idLivro}",method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody VendaPostRequest request,@PathVariable("idCliente") Long idCliente,@PathVariable("idFuncionario") Long idFuncionario,@PathVariable("idLivro") Long idLivro ){
		try {
			Optional<Cliente> consultCliente = clienteRepository.findById(idCliente);
			Optional<Funcionario> consultFuncionario = funcionarioRepository.findById(idFuncionario);
			Optional<Livro> consultLivro = livroRepository.findById(idLivro);
			
			
			if(consultCliente.isEmpty())
				throw new IllegalArgumentException("nao foi possivel encontrar o cliente");
			
			if(consultFuncionario.isEmpty())
				throw new IllegalArgumentException("nao foi possivel Encontrar o Funcionario");
			
			if(consultLivro.isEmpty())
				throw new IllegalArgumentException("nao foi possivel encontrar o Livro");
			
			
			Cliente cliente = consultCliente.get();
			Funcionario funcionario = consultFuncionario.get();
			Livro livro = consultLivro.get();
			
			List<Livro> livros = new ArrayList<Livro>();
			
			livros.add(livro);
			
			
			Date dataHora = new SimpleDateFormat("dd/MM/yyyy-HH:mm").parse(request.getData() + "-" + request.getHora());
			
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
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}
