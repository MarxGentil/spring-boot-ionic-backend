package com.nelioalves.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
//CAMADA DE ACESSO AOS DADOS (DAO)
	@Transactional(readOnly=true)  // faz a busca ficar mais rápida
	public List<Estado> findAllByOrderByNome();
}
