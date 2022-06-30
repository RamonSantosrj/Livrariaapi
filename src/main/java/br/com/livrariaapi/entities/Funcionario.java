package br.com.livrariaapi.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuncionario;
    @Column(nullable = false, length = 150)
    private String nome;
    @Column(nullable = false, length = 150, unique = true)
    private String email;
    @Column(nullable = false, length = 150, unique = true)
    private String cpf;
    @OneToMany(mappedBy = "funcionario")
    private List<Venda> Vendas;
}
