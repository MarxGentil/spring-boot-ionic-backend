package com.nelioalves.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1,"Pessoa Física"),
	PESSOAJURIDICA(2,"Pessoa Jurídica");
	
	int cod; 
	String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		if(cod==null) {
			return null;
		}
		
		// for que percorre todo os elementos do enum
		for (TipoCliente x : TipoCliente.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		//caso não encontre nada usa a exceção
		throw new IllegalArgumentException("id inválido: " + cod); 
	}
	
}
