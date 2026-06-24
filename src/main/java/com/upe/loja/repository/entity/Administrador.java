package com.upe.loja.repository.entity;

public class Administrador extends Usuario{
    
    //nao sei se tem algum atributo diferente

    public Administrador(){} //construtor vazio p JSON

    public Administrador(long cpf, String senha, String nome, long telefone, String email){
        super(cpf, senha, nome, telefone, email);
        //se add atributo ai faz o this.xx = xx normal
    }
    //getters e setters so se tiver algo adicionado?
}
