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

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'ID' é obrigatório.");
        }

        setNome(nome);
        setTaxaDiaria(taxaDiaria);
        setConservacao(conservacao);
        setValorReposicao(valorReposicao);
        setEstado(estado);
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

    //setters com verificação  
    public void setNome(String nome){
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Nome' é obrigatório.");
        }
        this.nome = nome;
    }
    public void setTaxaDiaria(BigDecimal taxaDiaria){ 
        if (taxaDiaria == null || taxaDiaria.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("A 'Taxa Diária' deve ser maior que zero.");
        }
        this.taxaDiaria = taxaDiaria;
    }
    public void setConservacao(String conservacao){ 
        if (conservacao == null || conservacao.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Conservação' é obrigatório.");
        }
        this.conservacao = conservacao;
    }
    public void setValorReposicao(BigDecimal valorReposicao){ 
        if (valorReposicao == null || valorReposicao.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O 'Valor de Reposição' deve ser maior que zero.");
        }
        this.valorReposicao = valorReposicao;
    }
    public void setEstado(EstadoProduto estado){ 
        if (estado == null) {
            throw new IllegalArgumentException("O campo 'Estado' é obrigatório.");
        }
        this.estado = estado;
    }

}