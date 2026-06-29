package com.upe.loja.repository.entity;

public class Fornecedor {
    private String id;
    private String nome;
    private String cnpj;
    private String telefone;

    // O Construtor: é aqui que a Facade empacota os dados de uma vez só
    public Fornecedor(String id, String nome, String cnpj, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }

    // Métodos Getters e Setters para acessar ou modificar os dados depois da caixa fechada
    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}