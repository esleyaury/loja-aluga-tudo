package com.upe.loja.repository.entity;

public abstract class Usuario {
    private String id;
    private long cpf;
    private String nome;
    private String email;

    
    public Usuario(String id, long cpf, String nome, String email){
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    public String getId(){
        return this.id;
    }

    public long getCpf(){
        return this.cpf;
    }

    public String getNome(){
        return this.nome;
    }

    public String getEmail(){
        return this.email;
    }

    //setters
    //os outros acho desnecessario a gente incluir ao menos inicialmente, vamos focar no básico
    public long setCpf(long cpf){return this.cpf = cpf;}
    public String setNome(String nome){return this.nome = nome;}
    public String setEmail(String email){return this.email = email;}

}
