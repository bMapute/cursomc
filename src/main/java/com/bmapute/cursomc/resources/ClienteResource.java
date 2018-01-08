package com.bmapute.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bmapute.cursomc.domain.Cliente;
import com.bmapute.cursomc.dto.ClienteDTO;
import com.bmapute.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping( value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente cliente=service.find(id);
	 
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO catDto, @PathVariable Integer id) {
		Cliente obj=service.fromDto(catDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Cliente> delete(@PathVariable Integer id) {

		service.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {

		List<Cliente> categorias = service.findAll();
		List<ClienteDTO> categoriasDto = categorias.stream().map(cat -> new ClienteDTO(cat))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriasDto);
	}
	
	@RequestMapping(value="/page",method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {

		Page<Cliente> categorias = service.findPage(page,linesPerPage,orderBy,direction);
		Page<ClienteDTO> categoriasDto = categorias.map(cat -> new ClienteDTO(cat));
		return ResponseEntity.ok().body(categoriasDto);
	}

	
	

}
