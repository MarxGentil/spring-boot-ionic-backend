package com.nelioalves.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nelioalves.cursomc.domain.Categoria;

//Classe para acesso aos dados

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
}
