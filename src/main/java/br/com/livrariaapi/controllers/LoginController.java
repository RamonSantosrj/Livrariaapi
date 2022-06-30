package br.com.livrariaapi.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.livrariaapi.Helpers.MD5Helper;
import br.com.livrariaapi.entities.Usuario;
import br.com.livrariaapi.repositories.IUsuarioRepository;
import br.com.livrariaapi.requests.UsuarioPostRequest;
import br.com.livrariaapi.security.TokenSecurity;


@Transactional
@Controller
public class LoginController {

	private static final String ENDPOINT = "/api/autenticar";
	@Autowired
	IUsuarioRepository usuarioRepository;

	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody UsuarioPostRequest request) {

		try {
			
			Usuario usuario = usuarioRepository.findByEmailAndSenha(request.getEmail(), MD5Helper.encrypt(request.getSenha()));

			if (usuario == null)
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado. Email e senha inválidos.");

			String token = TokenSecurity.generateToken(usuario.getEmail());

			return ResponseEntity.status(HttpStatus.OK).body(token);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
