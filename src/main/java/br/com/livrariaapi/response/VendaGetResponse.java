package br.com.livrariaapi.response;

import java.util.List;

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
public class VendaGetResponse {

	private long idVenda;
	private String dataHora;
	private String nomeFuncionario;
	private String emailFuncionario;
	private String nomeCliente;
	private String emailCliente;
	private double precoVenda;
	private List<LivroGetResponse> livros;
	
	

}
