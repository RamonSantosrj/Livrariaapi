package br.com.livrariaapi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.livrariaapi.entities.Funcionario;


public interface IFuncionarioRepository extends CrudRepository<Funcionario, Long> {

	@Query("select e from Funcionario e where e.email = :param1")
	Funcionario findByEmail(@Param("param1") String email) throws Exception;
	
	@Query("select e from Funcionario e where e.cpf = :param1")
	Funcionario findByCpf(@Param("param1") String cpf) throws Exception;
	
	
}
