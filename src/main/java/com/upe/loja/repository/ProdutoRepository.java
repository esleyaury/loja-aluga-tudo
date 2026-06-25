package com.upe.loja.repository;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.io.BufferedWriter;

import com.upe.loja.repository.entity.Produto;
import com.upe.loja.repository.entity.Produto.EstadoProduto;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ProdutoRepository {
  private Map<String, Produto> estoque;
  private final File arquivoProdutos= new File("produtos.csv");

  public ProdutoRepository(){
    this.estoque = carregar();
  }

  public Map<String, Produto> carregar(){

    Map <String, Produto> listaProdutos = new HashMap<>();
    try{
      if(!arquivoProdutos.exists()){
        arquivoProdutos.createNewFile();
        return listaProdutos;
      }
    
    List<String> linhas = Files.readAllLines(arquivoProdutos.toPath());
    for(String linha : linhas){
      if(linha.trim().isEmpty()){continue;}
        
        String[] dados = linha.split(";");
        if (dados.length != 6) {continue;}

        String id = dados[0];
        String nome = dados[1];
        BigDecimal taxaDiaria = new BigDecimal(dados[2]);
        String conservacao = dados[3];
        BigDecimal valorReposicao = new BigDecimal(dados[4]);
        EstadoProduto estado = EstadoProduto.valueOf(dados[5]); //aq pod colocar um toUpperCase()
        Produto produto = new Produto(id, nome, taxaDiaria, conservacao, valorReposicao, estado);
        
        listaProdutos.put(id, produto);
      }

    }catch(Exception e){
      System.err.println(e);
      e.printStackTrace();
    }

    return listaProdutos;
  } 
  
  public void salvar(Produto produto){
    estoque.put(produto.getID(), produto);
  }

  public List<Produto> listarTodos(){
    return new ArrayList<>(estoque.values());
  }

  public List<Produto> buscarProduto(String nome){
    return this.estoque.values().stream().filter(p -> p.getNome().equalsIgnoreCase(nome.trim()))
    .collect(Collectors.toList());
  }

  public Optional<Produto> buscarPorId(String id){
    return Optional.ofNullable(estoque.get(id)); // n concordo c isso n 
  }

  public void atualizar(Produto produto){
    estoque.put(produto.getID(), produto);
  }

  public void remover(String id){
    this.estoque.remove(id);
  }

  public void guardarDados(){
    try(BufferedWriter writer = Files.newBufferedWriter(arquivoProdutos.toPath())){
      for(Produto p : estoque.values()){
        String linha = String.format("%s;%s;%s;%s;%s;%s", p.getID(), p.getNome(), p.getTaxaDiaria(), p.getConservacao(), p.getValorReposicao(), p.getEstado());
        writer.write(linha);
        writer.newLine();
      }
    }catch(Exception e){
      System.err.println(e);
      e.printStackTrace();
    }
  }
}