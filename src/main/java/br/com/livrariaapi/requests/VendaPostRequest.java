package br.com.livrariaapi.requests;

import java.util.List;

import br.com.livrariaapi.entities.Cliente;
import br.com.livrariaapi.entities.Funcionario;
import br.com.livrariaapi.entities.Livro;
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
public class VendaPostRequest {

	ClientePostRequest clientePostRequest;
	FuncionarioPostRequest funcionarioPostRequest;
	List<LivroPostRequest> listLivro;
	private String data;
	private String hora;
}
