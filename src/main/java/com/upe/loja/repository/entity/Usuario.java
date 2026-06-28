package com.upe.loja.repository.entity;

public class Usuario {
    private long cpf;
    private String senha; // um cpf pode ser cliente e funcionario, por exemplo, oq vai diferenciar o acesso vai ser essa senha
    private String nome;
    private long telefone;
    private String email;

    public Usuario(long cpf, String senha, String nome, long telefone, String email){
        setCpf(cpf);
        setSenha(senha);
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
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

    //setters com verificação
    private void setCpf(long cpf){
        if (cpf <= 0) {
            throw new IllegalArgumentException("O campo 'CPF' é obrigatório e deve ser válido.");
        }
        this.cpf = cpf;
    }

    public void setSenha(String senha){
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Senha' é obrigatório.");
        }
        this.senha = senha;
    }

    public void setNome(String nome){
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Nome' é obrigatório.");
        }
        this.nome = nome;
    }

    public void setTelefone(long telefone){
        if (telefone <= 0) {
            throw new IllegalArgumentException("O campo 'Telefone' é obrigatório e deve ser válido.");
        }
        this.telefone = telefone;
    }

    public void setEmail(String email){
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("O campo 'Email' é obrigatório e deve ser válido.");
        }
        this.email = email;
    }

}
