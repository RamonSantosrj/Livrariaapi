package br.com.livrariaapi.controllers;


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

import br.com.livrariaapi.Helpers.MD5Helper;
import br.com.livrariaapi.entities.Usuario;
import br.com.livrariaapi.repositories.IUsuarioRepository;
import br.com.livrariaapi.requests.UsuarioPutRequest;
import br.com.livrariaapi.requests.UsuarioRegisterPostRequest;
import io.swagger.annotations.ApiOperation;


@Transactional
@Controller
public class RegisterUsuario {

	private static final String ENDPOINT = "/api/register";

	@Autowired
	IUsuarioRepository usuarioRepository;

	@ApiOperation("Metodo para realizar cadastro de Usuario")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody UsuarioRegisterPostRequest request) {

		try {

			if (usuarioRepository.findByEmail(request.getEmail()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Email ja cadastrado, Porfavor tente outro");
			}

			Usuario usuario = new Usuario();
			usuario.setNome(request.getNome());
			usuario.setEmail(request.getEmail());
			usuario.setSenha(MD5Helper.encrypt(request.getSenha()));
			usuarioRepository.save(usuario);

			return ResponseEntity.status(HttpStatus.CREATED).body("Usuario Cadastrado com sucesso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("metodo para realizar cadastro de Funcionario")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody UsuarioPutRequest request) {

		try {
			Optional<Usuario> consult = usuarioRepository.findById(request.getIdUsuario());

			if (consult.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Não foi possivel alterar Usuario, porfavor verifique o ID informado");
			}

			Usuario usuario = new Usuario();
			usuario.setIdUsuario(request.getIdUsuario());
			usuario.setNome(request.getNome());
			usuario.setSenha(request.getSenha());
			usuarioRepository.save(usuario);

			return ResponseEntity.status(HttpStatus.CREATED).body("Usuario atualizado com sucesso!");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("metodo para deletar Usuario")
	@RequestMapping(value = ENDPOINT + "{idUsuario}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idUsuario") Long idUsuario) {

		try {

			Optional<Usuario> consult = usuarioRepository.findById(idUsuario);

			if (consult.isPresent()) {
				Usuario usuario = consult.get();
				usuarioRepository.delete(usuario);
				return ResponseEntity.status(HttpStatus.OK).body("Usuario Deletado com sucesso com sucesso!");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possivel atualizar Usuario!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@ApiOperation("metodo para buscar um Usuario pelo id")
	@RequestMapping(value = ENDPOINT + "{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> getById(@PathVariable("idUsuario") Long idUsuario) {
		Usuario usuario = null;

		try {
			Optional<Usuario> consult = usuarioRepository.findById(idUsuario);

			if (consult.isPresent()) {
				usuario = consult.get();
				return ResponseEntity.status(HttpStatus.OK).body(usuario);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

}
