package com.bmapute.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.bmapute.cursomc.domain.Cidade;
import com.bmapute.cursomc.domain.Cliente;
import com.bmapute.cursomc.domain.Endereco;
import com.bmapute.cursomc.domain.enums.TipoCliente;
import com.bmapute.cursomc.dto.ClienteDTO;
import com.bmapute.cursomc.dto.ClienteNewDTO;
import com.bmapute.cursomc.repositories.CidadeRepository;
import com.bmapute.cursomc.repositories.ClienteRepository;
import com.bmapute.cursomc.repositories.EnderecoRepository;
import com.bmapute.cursomc.services.exceptions.DataIntegrityException;
import com.bmapute.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {

		Cliente cliente = repository.findOne(id);

		if (cliente != null) {
			return cliente;
		}
		throw new ObjectNotFoundException("Objecto nao encontrado! Id:" + id + ", Tipo: " + Cliente.class.getName());

	}

	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente= repository.save(cliente);
		enderecoRepository.save(cliente.getEnderecos());
		return cliente;
	}

	public Cliente update(Cliente cliente) {
		Cliente oldObj = find(cliente.getId());
		updateData(oldObj, cliente);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Nao e possivel excluir o Cliente pois tem pedidos associados!");
		}
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Cliente fromDto(ClienteDTO clienteDto) {
		return new Cliente(clienteDto.getId(), clienteDto.getNome(), clienteDto.getEmail(), null, null);
	}

	public Cliente fromDto(ClienteNewDTO clienteDto) {
		Cliente cli = new Cliente(null, clienteDto.getNome(), clienteDto.getEmail(), clienteDto.getCpfOuCnpj(),
				TipoCliente.toEnum(clienteDto.getTipoCliente()));
		Cidade cid = cidadeRepository.findOne(clienteDto.getCidadeId());
		Endereco end = new Endereco(null, clienteDto.getLogradouro(), clienteDto.getNumero(),
				clienteDto.getComplemento(), clienteDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(clienteDto.getTelefone1());
		if (clienteDto.getTelefone2() != null) {
			cli.getTelefones().add(clienteDto.getTelefone2());
		}
		if (clienteDto.getTelefone3() != null) {
			cli.getTelefones().add(clienteDto.getTelefone3());
		}
		return cli;
	}

	private void updateData(Cliente oldObj, Cliente cliente) {
		oldObj.setNome(cliente.getEmail());
		oldObj.setEmail(cliente.getEmail());
	}
}
