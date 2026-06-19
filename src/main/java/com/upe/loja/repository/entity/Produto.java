package com.upe.loja.repository.entity;
import java.math.BigDecimal;

public class Produto {
    // Tem que adicionar quantidade, e disponibilidade!!1 menob, faca isso!
    private String id;
    private String nome;
    private BigDecimal taxaDiaria;
    private String conservacao;
    private BigDecimal valorReposicao;
 //   private Categoria categoria;
   // private Fornecedor fornecedor;
    private String estado;

    public Produto(){}
    public Produto(String id, String nome, BigDecimal taxaDiaria,
        String conservacao, BigDecimal valorReposicao, String estado){

        this.id = id;
        this.nome = nome;
        this.taxaDiaria = taxaDiaria;
        this.conservacao = conservacao;
        this.valorReposicao = valorReposicao;
       // this.categoria = categoria;
     //   this.fornecedor = fornecerdor;
        this.estado = estado;
    }
    public String getID(){
        return this.id;
    }
    public String getNome(){
        return this.nome;
    }
    public BigDecimal getTaxaDiaria(){
        return this.taxaDiaria;
    }
    public String getConservacao(){
        return this.conservacao;
    }
    public BigDecimal getValorReposicao(){
        return this.valorReposicao;
    }
    public String getEstado(){
        return this.estado;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }
}
