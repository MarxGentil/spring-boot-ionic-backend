package com.nelioalves.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nelioalves.cursomc.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // Geração de chave primária
	private Integer id;
	private String nome;
	
	@Column(unique=true)
	private String email;

	private String cpfOucnpj;
	private Integer tipo;
	
	//No modelo, um cliente tem vários endereços
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL) // cliente é o nome do mapeamento da classe Endereco 	private Cliente cliente;
	private List<Endereco> enderecos = new ArrayList<>();
	
	//No modelo, a classe Telefone é símples, só contém um atributo, então, 
	//optaremos pela abordagem de criar uma coleção de Strings. Coleção não aceita repetição.
	@ElementCollection
	@CollectionTable(name="TELEFONE") //TELEFONE será o nome da tabela
	private Set<String> telefones = new HashSet<>();

	//os clientes tem um relacionamento bi-direcional com pedido. Um cliente pode fazer vários pedidos
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente() {
		
	}

	public Cliente(Integer id, String nome, String email, String cpfOucnpj, TipoCliente tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOucnpj = cpfOucnpj;
		this.tipo = (tipo==null) ? null : tipo.getCod();
	}

	public Integer getid() {
		return id;
	}

	public void setid(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOucnpj() {
		return cpfOucnpj;
	}

	public void setCpfOucnpj(String cpfOucnpj) {
		this.cpfOucnpj = cpfOucnpj;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
