package com.bmapute.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.bmapute.cursomc.domain.Cliente;
import com.bmapute.cursomc.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
private Integer id;

@NotEmpty(message="Campo obrigatorio")
@Length(min=5, max=120, message="O tamnho deve ter entre 5 a 120 caracters ")
private String nome;

@NotEmpty(message="Campo obrigatorio")
@Email(message="Email invalido")
private String email;
	
	public Integer getId() {
		return id;
	}
	
	public ClienteDTO() {
	}
	
public	ClienteDTO(Cliente obj) {
	this.id=obj.getId();
	this.nome=obj.getNome();
	this.email=obj.getEmail();
}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
	