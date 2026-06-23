package com.upe.loja.business;
import com.upe.loja.business.interfaces.ProdutoInterface;
import com.upe.loja.repository.ProdutoRepository;
import com.upe.loja.repository.entity.Produto;
import java.util.List;
import java.util.ArrayList;

public class ProdutoBusiness implements ProdutoInterface{
  private int quantidadePedido;
  private ProdutoRepository produto;

  public ProdutoBusiness(int quantidadePedido){
    this.quantidadePedido = quantidadePedido;
    this.produto = new ProdutoRepository();
  }


  @Override
    public Produto verificarDisponibilidade(ProdutoRepository produto){
      
    }

  

}
