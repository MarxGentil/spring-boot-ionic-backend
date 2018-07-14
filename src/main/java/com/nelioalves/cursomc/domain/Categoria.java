package com.nelioalves.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Categoria implements Serializable {

// CAMADA DE DOMÍNIO (REGRAS DE NEGÓCIOS)
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // Geração de chave primária
	private Integer id;
	private String nome;

	@JsonManagedReference // ação para permitir que produto e categoria não fiquem em loop na consulta
	@ManyToMany(mappedBy="categorias") //categorias é o nome da lista que se encontra na classe Produto que é a classe que tem associação com Categoria e que já está mapeada como Muitos para Muitos
	private List<Produto> produtos = new ArrayList<>(); 
	// onde produtos é o nome da ASSOCIAÇÃO e não o nome da classe ou da entidade
	
	public Categoria() { //Construtor Vazio
		
	}

	public Categoria(Integer id, String nome) { // Construtor que criei pelo java com os atributos
		super();
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
	
}
