package com.upe.loja.repository;
import java.io.File;
import java.io.IOException;
import com.upe.loja.repository.entity.Produto;
import java.util.Map;
import java.util.HashMap;

//Jackson JSON
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class ProdutoRepository {
  private ObjectMapper mapper;
  private Map<String, Produto> estoque;
  private Produto produto;
  private final File arquivoDestino = new File("produtos.json");

  public ProdutoRepository(Produto produto){
    this.mapper = new ObjectMapper();
    this.produto = produto;
    this.estoque = carregar();
  }

  public Map<String, Produto> carregar(){

    try{

      Map<String, Produto> objetoCarregado = HashMap<>();
      objetoCarregado = mapper.readValue(arquivoDestino, new TypeReference<Map<String, Produto>>(){});
      return objetoCarregado;

    } catch (Exception e){
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }
  public void salvar(Produto produto){
    this.estoque = carregar();
    estoque.put(produto.getID(), produto);

    try {
      File arquivoDestino = new File("produtos.json");
      mapper.writerWithDefaultPrettyPrinter()
        .writeValue(arquivoDestino, estoque);
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

<<<<<<< HEAD
  public Produto buscarProduto(){};
  public void atualizar(Produto produto){};
  public void remover(String id){};
=======
  public Produto buscarProduto(){

  }

  public void atualizar(Produto produto);
  public void remover(String id)
>>>>>>> 5b210dd (fix: fix sintaxe error)
}
