package com.bmapute.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmapute.cursomc.domain.Pedido;
import com.bmapute.cursomc.repositories.PedidoRepository;
import com.bmapute.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	
	public Pedido find(Integer id) {
		
		Pedido pedido=repository.findOne(id);
		
		if(pedido!=null) {
			return pedido;
		}
		throw new ObjectNotFoundException("Objecto nao encontrado! Id:"+id
				+", Tipo: "+Pedido.class.getName());
		
	}
}
