package com.upe.loja.repository;
import java.io.File;
import java.math.BigDecimal;

import com.upe.loja.repository.entity.Produto;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//Jackson JSON
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;

public class ProdutoRepository {
  private ObjectMapper mapper;
  private Map<String, Produto> estoque;
  private final File arquivoDestino = new File("produtos.json");

  public ProdutoRepository(){
    this.mapper = new ObjectMapper();
    this.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    this.estoque = carregar();
  }

  public Map<String, Produto> carregar(){

    try{
      Map<String, Produto> objetoCarregado = new HashMap<>();
      objetoCarregado = mapper.readValue(arquivoDestino, new TypeReference<Map<String, Produto>>(){});
      return objetoCarregado;
    } catch (Exception e){
      System.err.println(e.getMessage());
      e.printStackTrace();
      return new HashMap<>();
    }
  }
  
  public void salvar(Produto produto){
    //Rever a logica
    this.estoque = carregar();
    estoque.put(produto.getID(), produto);

    try {
      File arquivoDestino = new File("produtos.json");
      mapper.writerWithDefaultPrettyPrinter()
        .writeValue(arquivoDestino, estoque);
    } catch (Exception e){
        System.err.println(e);
        e.printStackTrace();
    }
  }

  public List<Produto> listarTodos(){
    if (this.estoque.isEmpty()){
      return new ArrayList<>();
    }

    return new ArrayList<>(estoque.values());
    /*Nao ha certeza de que vai carregar o estoque
    totalmente atualizado!! */

  }

  public List<Produto> buscarProduto(String nome){
    try{
    return this.estoque.values().stream().filter(p -> p.getNome().equalsIgnoreCase(nome))
    .collect(Collectors.toList());
    }catch (Exception e){
      System.err.println(e);
      e.printStackTrace();
      return new ArrayList<>();
    }
    /*Nao ha certeza de que vai carregar o estoque
    totalmente atualizado!! */
  }

  public Optional<Produto> buscarPorId(String id){
    return Optional.ofNullable(estoque.get(id)); // n concordo c isso n 
  }

  public void atualizar(Produto produto, int option, String valor){
     switch(option){
      /* 
      Menu:
      Case 1 - Nome
      Case 2 - Taxa Diaria
      Case 3 - Conservacao
      Case 4 - Estado
      */
      case 1 -> produto.setNome(valor);
      case 2 -> produto.setTaxaDiaria(new BigDecimal(valor));
      case 3 -> produto.setConservacao(valor);
      case 4 -> produto.setEstado(Produto.
        EstadoProduto.valueOf(valor.toUpperCase())); 
     }

     //Salvar
    try {
          mapper.writerWithDefaultPrettyPrinter()
            .writeValue(this.arquivoDestino, estoque);
        } catch (Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
  }

  public void remover(String id){
    this.estoque.remove(id);
    // Salvar
    try {
          mapper.writerWithDefaultPrettyPrinter()
            .writeValue(this.arquivoDestino, estoque);
        } catch (Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
  }
}
