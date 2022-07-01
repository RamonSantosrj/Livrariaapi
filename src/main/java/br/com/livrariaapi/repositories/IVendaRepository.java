package br.com.livrariaapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.livrariaapi.entities.Venda;

public interface IVendaRepository extends CrudRepository<Venda, Long> {

	@Query("select distinct a from Venda a join a.funcionario join a.livros where a.cliente.idCliente = :param1")
	public List<Venda> findbyCliente(@Param("param1") long idCliente ) throws Exception;
}
