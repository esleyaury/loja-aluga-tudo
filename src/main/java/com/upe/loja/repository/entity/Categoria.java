package com.upe.loja.repository.entity;

public class Categoria {
    private String nomeCategoria;

    public Categoria(String nome){
        this.nomeCategoria = nome;
    }

    public String getNomeCategoria(){
        return this.nomeCategoria;
    }

}
