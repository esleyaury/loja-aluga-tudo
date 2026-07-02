package com.upe.loja.repository;
import java.io.File;

import com.upe.loja.repository.entity.Produto;
import com.upe.loja.repository.entity.Produto.EstadoProduto;
import com.upe.loja.repository.interfaces.IProdutoRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;

public class ProdutoRepository implements IProdutoRepository{
  private Map<String, Produto> estoque;
  private GerirCSV<Produto> gerenciadorArquivo;
  private File arquivoProdutos;

  public ProdutoRepository(){
    this.arquivoProdutos = new File("produtos.csv");
    this.gerenciadorArquivo = new GerirCSV<>();

    // Formato da linha: id;nome;categoria;taxaDiaria;conservacao;valorReposicao;estado
    List<Produto> lista = gerenciadorArquivo.carregar(this.arquivoProdutos, linha -> {
      String[] dados = linha.split(";");
      if (dados.length != 7) { return null; }

      BigDecimal taxaDiaria = new BigDecimal(dados[3]);
      BigDecimal valorReposicao = new BigDecimal(dados[5]);
      EstadoProduto estado = EstadoProduto.valueOf(dados[6].toUpperCase());
      return new Produto(dados[0], dados[1], dados[2], taxaDiaria, dados[4], valorReposicao, estado);
    });

    this.estoque = new HashMap<>();
    for (Produto produto : lista) {
      this.estoque.put(produto.getID(), produto);
    }
  }

  public void salvar(Produto produto){
    estoque.put(produto.getID(), produto);
  }

  public Produto buscarPorId(String id){
    return estoque.get(id);
  }

  public Map<String, Produto> listarTodos(){
    return new HashMap<>(estoque);
  }
  //vai pro business?
  public List<Produto> buscarProduto(String nome){
    return this.estoque.values().stream().filter(p -> p.getNome().equalsIgnoreCase(nome.trim()))
    .collect(Collectors.toList());
  }

  public void atualizar(Produto produto){
    estoque.put(produto.getID(), produto);
  }

  public void remover(String id){
    this.estoque.remove(id);
  }

  public void guardarDados(){
      gerenciadorArquivo.guardarDados(this.arquivoProdutos, this.estoque.values(), p ->
          String.format("%s;%s;%s;%s;%s;%s;%s", p.getID(), p.getNome(), p.getCategoria(),
              p.getTaxaDiaria(), p.getConservacao(), p.getValorReposicao(), p.getEstado()));
  }
}
