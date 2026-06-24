package com.upe.loja.repository.entity;

public class Funcionario extends Usuario{

    //vai ter atributos diferentes?

    public Funcionario(long cpf, String senha, String nome, long telefone, String email){
        super(cpf, senha, nome, telefone, email);
        //se tiver atributos diferentes fazer o this.xx = xx normal
    }

    //getters e setters so se add algo?

}
