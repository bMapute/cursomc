package com.bmapute.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmapute.cursomc.domain.Cliente;
import com.bmapute.cursomc.repositories.ClienteRepository;
import com.bmapute.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	public Cliente buscar(Integer id) {
		
		Cliente cliente=repository.findOne(id);
		
		if(cliente!=null) {
			return cliente;
		}
		throw new ObjectNotFoundException("Objecto nao encontrado! Id:"+id
				+", Tipo: "+Cliente.class.getName());
		
	}
}
