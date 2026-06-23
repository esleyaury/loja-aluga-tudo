package com.upe.loja.repository;
import java.io.File;
import com.upe.loja.repository.entity.Produto;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Jackson JSON
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class ProdutoRepository {
  private ObjectMapper mapper;
  private Map<String, Produto> estoque;
  private final File arquivoDestino = new File("produtos.json");

  public ProdutoRepository(){
    this.mapper = new ObjectMapper();
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

  public void listarTodos(){
    this.estoque = carregar();
    if (estoque.isEmpty()){
      System.out.println("Nenhum produto encontrado!");
    }

    estoque.forEach((id, produto)->{System.out.println("ID: " +id +
          " | Nome: " + produto.getNome() + " | Taxa Diaria " + produto.getTaxaDiaria() +
          " | Conservacao: " + produto.getConservacao());
    });

  }

  public List<Produto> buscarProduto(String nome){
    Map<String, Produto> mapaProdutos = carregar();
    try{
    return mapaProdutos.values().stream().filter(p -> p.getNome().equalsIgnoreCase(nome))
    .collect(Collectors.toList());
    }catch (Exception e){
      System.err.println(e);
      e.printStackTrace();
      return new ArrayList<>();
    }

  }

  //public void atualizar(Produto produto, int option, String msg)

  public void remover(String id){ this.estoque.remove(id); }
}
