package br.com.livrariaapi.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Venda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idVenda;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, length = 150)
	private Date dataHora;
	@ManyToOne
	@JoinColumn(name = "idFuncionario", nullable = false)
	private Funcionario funcionario;
	@ManyToOne
	@JoinColumn(name = "idCliente", nullable = false)
	private Cliente cliente;
	@ManyToMany
	@JoinTable(name = "venda_Livro", 
	joinColumns = @JoinColumn(name = "idVenda", nullable = false), 
	inverseJoinColumns = @JoinColumn(name = "idLivro", nullable = false)
	)
	private List<Livro> livros;
	@Column ( nullable = false)
	private Double preco;
}