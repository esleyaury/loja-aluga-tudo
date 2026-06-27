package com.upe.loja.repository.entity;

public class Categoria {
    private String nomeCategoria;

    public Categoria(String nome){
        setNome(nome);
    }

    public String getNomeCategoria(){
        return this.nomeCategoria;
    }

    public void setNome(String nome){
        if(nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        this.nomeCategoria = nome;
    }

}
