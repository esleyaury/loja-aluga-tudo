package com.upe.loja.repository.entity;
import java.math.BigDecimal;

public class Produto {
    private String id;
    private String nome;
    private BigDecimal taxaDiaria;
    private String conservacao;
    private BigDecimal valorReposicao;
    private EstadoProduto estado;

    public Produto(){}
    public Produto(String id, String nome, BigDecimal taxaDiaria,
        String conservacao, BigDecimal valorReposicao, EstadoProduto estado){

        this.id = id;
        this.nome = nome;
        this.taxaDiaria = taxaDiaria;
        this.conservacao = conservacao;
        this.valorReposicao = valorReposicao;
        this.estado = estado;
    }

    public enum EstadoProduto{
      DISPONIVEL,
      ALUGADO,
      EM_MANUTENCAO,
      INATIVO
    }

    //getters 
    public String getID(){ return this.id; }
    public String getNome(){ return this.nome; }
    public BigDecimal getTaxaDiaria(){ return this.taxaDiaria;}
    public String getConservacao(){ return this.conservacao;}
    public BigDecimal getValorReposicao(){ return this.valorReposicao;}
    public EstadoProduto getEstado(){ return this.estado;}

    //setters
    public void setNome(String nome){this.nome = nome;}
    public void setTaxaDiaria(BigDecimal taxaDiaria){ this.taxaDiaria = taxaDiaria;}
    public void setConservacao(String conservacao){ this.conservacao = conservacao;}
    public void setValorReposicao(BigDecimal valorReposicao){ this.valorReposicao = valorReposicao;}
    public void setEstado(EstadoProduto estado){ this.estado = estado;}


    public String toString() {
        return "-----------------------------------\n" +
            "ID: " + this.id + "\n" +
            "Nome: " + this.nome + "\n" +
            "Taxa Diária: R$ " + this.taxaDiaria + "\n" +
            "Conservação: " + this.conservacao + "\n" +
            "Valor Reposição: R$ " + this.valorReposicao + "\n" +
            "Estado: " + this.estado + "\n" +
            "-----------------------------------";
    }
}