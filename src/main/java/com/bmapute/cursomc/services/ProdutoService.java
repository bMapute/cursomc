package com.bmapute.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bmapute.cursomc.domain.Categoria;
import com.bmapute.cursomc.domain.Produto;
import com.bmapute.cursomc.repositories.CategoriaRepository;
import com.bmapute.cursomc.repositories.ProdutoRepository;
import com.bmapute.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		
		Produto Produto=repository.findOne(id);
		
		if(Produto!=null) {
			return Produto;
		}
		throw new ObjectNotFoundException("Objecto nao encontrado! Id:"+id
				+", Tipo: "+Produto.class.getName());
			}
	
	public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias=categoriaRepository.findAll(ids);
		return repository.search(nome,categorias,pageRequest);
	}
}
