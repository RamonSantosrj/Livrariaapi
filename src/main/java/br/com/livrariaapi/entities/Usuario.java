package br.com.livrariaapi.entities;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	@Column(nullable = false,length = 150)
	private String nome;
	@Column(nullable = false,length = 150, unique = true)
	private String email;
	@Column(nullable = false,length = 150)
	private String senha;
}
