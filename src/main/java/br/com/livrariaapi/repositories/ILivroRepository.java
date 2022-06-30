package br.com.livrariaapi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.livrariaapi.entities.Livro;


public interface ILivroRepository extends CrudRepository<Livro, Long>{

	@Query("select e from Livro e where e.nome = :param1 ")
	Livro findByNome(@Param("param1") String nome) throws Exception;
	
	
	
}
