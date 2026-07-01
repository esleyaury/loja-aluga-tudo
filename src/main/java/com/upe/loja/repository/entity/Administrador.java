package com.upe.loja.repository.entity;

public class Administrador extends Funcionario{


    public Administrador(String id, String cpf, String senha, String nome,
        String email
    ){
        super(id, cpf, senha, nome, email, true);
    }
}
