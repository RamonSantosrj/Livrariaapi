package br.com.livrariaapi.Helpers;

import java.util.List;

import br.com.livrariaapi.entities.Livro;

public class CalcularVendaHelper {
	
	public static Double CalcularVenda(List<Livro> lista) {
		Double valor = 0.0;
		for(Livro livro : lista) {
			valor += livro.getPreco() ;		
		}
		
		return valor;
	}

}
