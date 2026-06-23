package com.upe.loja;
import com.upe.loja.repository.entity.*;
import java.math.BigDecimal;

public class Main{
  public static void main(String[] args) {
    Produto produto = new Produto("b52400", "Menob", new BigDecimal("22.2"), "Ben doente", new BigDecimal("0.0"), "Em manutenzion");
    ProdutoRepository produtoEstoque = new ProdutoRepository(produto);

    produtoEstoque.salvarProduto();
    produtoEstoque.lerProduto();
  }
}

