package com.upe.loja.repository.entity;

public class Usuario {
    private long cpf;
    private String senha; // um cpf pode ser cliente e funcionario, por exemplo, oq vai diferenciar o acesso vai ser essa senha
    private String nome;
    private long telefone;
    private String email;

    public Usuario(long cpf, String senha, String nome, long telefone, String email){
        this.cpf = cpf;
        this.senha = senha;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;

    }

    public long getCpf(){
        return this.cpf;
    }

    public String getSenha(){
        return this.senha;
    }

    public String getNome(){
        return this.nome;
    }

    public long getTelefone(){
        return this.telefone;
    }

    public String getEmail(){
        return this.email;
    }

    //setters
    //os outros acho desnecessario a gente incluir ao menos inicialmente, vamos focar no básico

    public String setSenha(String senha){return this.senha = senha;}

    public long setTelefone(long telefone){return this.telefone = telefone;}

    public String setEmail(String email){return this.email = email;}

}
