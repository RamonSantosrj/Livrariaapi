package br.com.livrariaapi.repositories;

import org.springframework.data.repository.CrudRepository;

import br.com.livrariaapi.entities.Venda;

public interface IVendaRepository extends CrudRepository<Venda, Long> {

}
