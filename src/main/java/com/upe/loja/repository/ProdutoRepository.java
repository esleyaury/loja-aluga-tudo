package com.upe.loja.repository;
import java.io.File;

import com.upe.loja.repository.entity.Produto;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;

public class ProdutoRepository implements IProdutoRepository{
  private Map<String, Produto> estoque;
  private GerirProdutosCSV gerenciadorArquivo;
  private File arquivoProdutos;

  public ProdutoRepository(){
    this.arquivoProdutos = new File("produtos.csv");
    this.gerenciadorArquivo = new GerirProdutosCSV();
    this.estoque = gerenciadorArquivo.carregar(this.arquivoProdutos);
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
      gerenciadorArquivo.guardarDados(this.arquivoProdutos, this.estoque);
  }
}