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
public class FuncionarioPutRequest {
	private long idFuncionario;
	private String nome;
	private String email;
	private String cpf;
	
}
