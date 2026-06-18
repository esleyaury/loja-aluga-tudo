package com.upe.loja.repository.entity;

public abstract class Usuario{
	
	private String cpf;
	private String nome;
	private String email;
	private String endereco;
	
	public void Usuario(String cpf, String nome, String email, String endereco) {
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
	}
	public String getCpf() {
		return this.cpf;
	}
	public String getNome() {
		return this.nome;
	}
	public String getEmail() {
		return this.email;
	}
	public String getEndereco() {
		return this.endereco;
	}
}