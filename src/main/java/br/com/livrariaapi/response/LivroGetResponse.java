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
public class LivroGetResponse {

	private long idLivro;
	private String nome;
	private String autor;
	private Double preco;
	
}
