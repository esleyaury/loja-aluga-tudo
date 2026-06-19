package com.upe.loja.repository;
import java.io.File;
import java.io.IOException;
import com.upe.loja.repository.entity.*;

//import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProdutoRepository {
  private ObjectMapper mapper;
  private Produto produto;

  public ProdutoRepository(Produto produto){
    this.mapper = new ObjectMapper();
    this.produto = produto;
  }

  public void salvarProduto(){
    try {
      this.mapper.writerWithDefaultPrettyPrinter().writeValue(new File("produto.json"), this.produto);
    } catch (IOException e){
      e.printStackTrace();
    }
  }
}
