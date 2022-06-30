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

import br.com.livrariaapi.entities.Livro;
import br.com.livrariaapi.repositories.ILivroRepository;
import br.com.livrariaapi.requests.LivroPostRequest;
import br.com.livrariaapi.requests.LivroPutRequest;
import io.swagger.annotations.ApiOperation;

@Transactional
@Controller
public class LivroController {

	private static final String ENDPOINT = "/api/livro";

	@Autowired
	private ILivroRepository livroRepository;

	@ApiOperation("Metodo para Realizar cadastro de livros no sistema")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody LivroPostRequest request) {

		try {
			if (livroRepository.findByNome(request.getNome()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("O " + request.getNome() + " Livro ja esta Cadastrado");
			}

			Livro livro = new Livro();

			livro.setNome(request.getNome());
			livro.setAutor(request.getAutor());
			livro.setPreco(request.getPreco());

			livroRepository.save(livro);

			return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro Realizado com sucesso.");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}

	}

	@ApiOperation("Metodo para Realizar a Atualização de um livro")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody LivroPutRequest request) {

		try {
			Optional<Livro> consult = livroRepository.findById(request.getIdLivro());

			if (consult.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Nenhum livro Encontrado, Por favor verifique o Id informado.");
			}

			Livro livro = consult.get();

			livro.setNome(request.getNome());
			livro.setAutor(request.getAutor());
			livro.setPreco(request.getPreco());

			livroRepository.save(livro);

			return ResponseEntity.status(HttpStatus.CREATED).body("Atualizado com sucesso!");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("Metodo para pegar todos os livros Cadastrados")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	public ResponseEntity<List<Livro>> getAll() {
		List<Livro> livros = null;
		
		try {
			livros = (List<Livro>) livroRepository.findAll();
			
			if(livros.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(livros);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
	}

	@ApiOperation("Realizar consulta de um livro pelo id")
	@RequestMapping(value = ENDPOINT + "{idLivro}", method = RequestMethod.GET)
	public ResponseEntity<Livro> getById(@PathVariable("idLivro") Long idLivro) {
		Livro livro = null;
		
		
		
		try {
			Optional<Livro> consult = livroRepository.findById(idLivro);
			if(consult.isPresent()) {
				livro = consult.get();
				return ResponseEntity.status(HttpStatus.OK).body(livro);
			}
			
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@ApiOperation("Metodo para Excluir um livro")
	@RequestMapping(value = ENDPOINT + "{idLivro}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idLivro") Long idLivro) {
		
		try {
			Livro livro = null;
			
			Optional<Livro> consult = livroRepository.findById(idLivro);
			
			if(consult.isPresent()) {
				livro = consult.get();
				livroRepository.delete(livro);
				return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possivel deletar o livro!");
			
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		
		
	}

}
