package br.com.livrariaapi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.livrariaapi.entities.Cliente;
import br.com.livrariaapi.entities.Funcionario;

public interface IClienteRepository extends CrudRepository<Cliente, Long>{

	@Query("select e from Cliente e where e.email = :param1")
	Cliente findByEmail(@Param("param1") String email) throws Exception;
	
	@Query("select e from Cliente e where e.cpf = :param1")
	Cliente findByCpf(@Param("param1") String cpf) throws Exception;
}
