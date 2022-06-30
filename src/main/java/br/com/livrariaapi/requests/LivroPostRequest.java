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
public class LivroPostRequest {

	private String nome;
	private String autor;
	private Double Preco;
	
}
