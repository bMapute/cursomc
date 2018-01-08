package com.bmapute.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bmapute.cursomc.domain.Categoria;
import com.bmapute.cursomc.dto.CategoriaDTO;
import com.bmapute.cursomc.repositories.CategoriaRepository;
import com.bmapute.cursomc.services.exceptions.DataIntegrityException;
import com.bmapute.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria find(Integer id) {

		Categoria categoria = repository.findOne(id);

		if (categoria != null) {
			return categoria;
		}
		throw new ObjectNotFoundException("Objecto nao encontrado! Id:" + id + ", Tipo: " + Categoria.class.getName());

	}

	public Categoria insert(Categoria cat) {
		cat.setId(null);
		return repository.save(cat);
	}

	public Categoria update(Categoria cat) {
		Categoria oldObj=find(cat.getId());
		updateData(oldObj,cat);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Nao e possivel excluir a categoria pois existem produtos associados!");
		}
	}

	public List<Categoria> findAll() {
		return repository.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Categoria fromDto(CategoriaDTO catDto) {
		return new Categoria(catDto.getId(), catDto.getNome());
	}
	
	private void updateData(Categoria oldObj, Categoria obj) {
		oldObj.setNome(obj.getNome());
	}
}
