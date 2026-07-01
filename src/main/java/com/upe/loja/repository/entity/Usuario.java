package com.upe.loja.repository.entity;

public abstract class Usuario {
    private String id;
    private String cpf;
    private String senha;
    private String nome;
    private String email;
    private TipoPerfil tipo;
    private boolean ativo;
    
    public Usuario(String id, String cpf, String senha, String nome, String email, TipoPerfil tipo){
        this.id = id;
        this.cpf = cpf;
        this.senha = senha;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        this.ativo = true;
    }

    public enum TipoPerfil{
        FUNCIONARIO,
        CLIENTE,
        ADMINISTRADOR

    }
    

    public String getId(){
        return this.id;
    }

    public String getCpf(){
        return this.cpf;
    }

    public String getSenha(){
        return this.senha;
    }

    public String getNome(){
        return this.nome;
    }

    public String getEmail(){
        return this.email;
    }
    public TipoPerfil getTipo(){
        return this.tipo;
    }

    public boolean isAtivo(){ return this.ativo;}

    //setters
    //os outros acho desnecessario a gente incluir ao menos inicialmente, vamos focar no básico
    public void setCpf(String cpf){this.cpf = cpf;}
    public void setTipoPerfil(TipoPerfil tipo){this.tipo = tipo;}
    public void setSenha(String senha){this.senha = senha;}
    public void setNome(String nome){this.nome = nome;}
    public void setEmail(String email){this.email = email;}
    public void setAtivo(boolean ativo){ this.ativo = ativo; }

}
