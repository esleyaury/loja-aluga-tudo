package com.upe.loja.repository.entity;

import java.util.HashSet;
import java.util.Set;

public class Fornecedor {
    private String cnpj;
    private String razaoSocial;
    private Set<String> produtos;

    public Fornecedor(String cnpj, String razaoSocial, Set<String> produtos){
        if (cnpj == null || cnpj.isBlank()){
            throw new IllegalArgumentException("CNPJ é obrigatório.");
        }
        if (razaoSocial == null || razaoSocial.isBlank()){
            throw new IllegalArgumentException("Razão social é obrigatória.");
        }

        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.produtos = produtos != null ? produtos : new HashSet<>();
    }

    public String getCnpj(){
        return this.cnpj;
    }

    public String getRazaoSocial(){
        return this.razaoSocial;
    }

    public Set<String> getProdutos(){
        return this.produtos;
    }

    public void setCnpj(String cnpj){
        this.cnpj = cnpj;
    }

    public void setRazaoSocial(String razaoSocial){
        this.razaoSocial = razaoSocial;
    }

    public void setProdutos(Set<String> produtos){
        this.produtos = produtos;
    }
}
