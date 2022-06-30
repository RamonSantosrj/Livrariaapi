package br.com.livrariaapi.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioPutRequest {
	
	private Long idUsuario;
	private String nome;
	private String senha;
}
