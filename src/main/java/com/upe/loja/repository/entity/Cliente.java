package com.upe.loja.repository.entity;

public class Cliente extends Usuario{
    //atributos diferentes?

    public Cliente(long cpf, String senha, String nome, long telefone, String email){
        super(cpf, senha, nome, telefone, email);
        //se add atributos colocar o this.xx = xx normal aqui
    }

    //getters e setters so se add coisas diferentes?
}
