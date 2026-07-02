package com.upe.loja.repository.entity;

public class Administrador extends Funcionario{


    public Administrador(String cpf, String senha, String nome, String email){
        super(cpf, senha, nome, email, true);
    }
}
