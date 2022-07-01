package br.com.livrariaapi.response;

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
public class FuncionarioGetResponse {

	private long idFuncionario;
	private String nomeString;
	private String cpf;
	private String email;
}
