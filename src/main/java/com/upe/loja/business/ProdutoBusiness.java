package com.upe.loja.business;
import com.upe.loja.repository.entity.Produto;
import java.util.List;
import java.util.ArrayList;

public class ProdutoBusiness{
  private List<Produto> produtos;
  private int quantidadePedido;

  public ProdutoBusiness(int quantidadePedido){
    this.produto = new ArrayList<>();
    this.quantidadePedido = quantidadePedido;
  }
}
