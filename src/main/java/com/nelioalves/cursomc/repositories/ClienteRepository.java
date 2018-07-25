package com.nelioalves.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
//CAMADA DE ACESSO AOS DADOS (DAO)
	@Transactional(readOnly=true)  // faz a busca ficar mais rápida
	Cliente findByEmail(String email);
}
