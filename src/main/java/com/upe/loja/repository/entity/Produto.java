package com.upe.loja.repository.entity;
import java.math.BigDecimal;

public class Produto {
    private String id;
    private String nome;
    private BigDecimal taxaDiaria;
    private String conservacao;
    private BigDecimal valorReposicao;
    private boolean estado;

    public Produto(String id, String nome, BigDecimal taxaDiaria,
        String conservacao, BigDecimal valorReposicao, boolean estado){

        this.id = id;
        this.nome = nome;
        this.taxaDiaria = taxaDiaria;
        this.conservacao = conservacao;
        this.valorReposicao = valorReposicao;
        this.estado = estado;
    }

    //getters 
    public String getID(){ return this.id; }
    public String getNome(){ return this.nome; }
    public BigDecimal getTaxaDiaria(){ return this.taxaDiaria;}
    public String getConservacao(){ return this.conservacao;}
    public BigDecimal getValorReposicao(){ return this.valorReposicao;}
    public boolean getEstado(){ return this.estado;}

    //setters
    public void setTaxaDiaria(BigDecimal taxaDiaria){ this.taxaDiaria = taxaDiaria;}
    public void setConservacao(String conservacao){ this.conservacao = conservacao;}
    public void setValorReposicao(BigDecimal valorReposicao){ this.valorReposicao = valorReposicao;}
    public void setEstado(boolean estado){ this.estado = estado;}
    // Tireando ID, e Nome, o resto pode mudar.
    // TODO: Discutir com os amiguinhos sobre a relevancia de ter outros setters.
}
