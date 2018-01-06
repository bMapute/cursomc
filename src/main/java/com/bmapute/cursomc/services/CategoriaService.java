package com.bmapute.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmapute.cursomc.domain.Categoria;
import com.bmapute.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public Categoria buscar(Integer id) {
		
		Categoria categoria=repository.findOne(id);
		return categoria;
	}
}
