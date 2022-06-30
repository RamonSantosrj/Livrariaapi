package br.com.livrariaapi.controllers;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Transactional
@Controller
public class RecuperarSenhaController {
	
	private static final String ENDPOINT ="/api/recuperar-senha";
	
	public ResponseEntity<String>post(){
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
	

}
