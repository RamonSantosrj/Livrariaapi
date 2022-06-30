package br.com.livrariaapi.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.livrariaapi.entities.Funcionario;
import br.com.livrariaapi.entities.Usuario;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {
	@Query("select e from Usuario e where e.email = :param1 and e.senha = :param2")
	Usuario findByEmailAndSenha(@Param("param1") String email,@Param("param2") String senha) throws Exception;
	
	@Query("select e from Usuario e where e.email = :param1")
	Usuario findByEmail(@Param("param1") String email) throws Exception;
}
