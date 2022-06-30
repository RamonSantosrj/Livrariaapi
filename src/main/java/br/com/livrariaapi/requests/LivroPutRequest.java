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
public class LivroPutRequest {
	
	private Long idLivro;
	private String nome;
	private String autor;
	private Double preco;
}
