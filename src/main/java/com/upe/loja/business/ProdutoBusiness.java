package com.upe.loja.business;
import com.upe.loja.business.interfaces.ProdutoInterface;
import com.upe.loja.repository.ProdutoRepository;
import com.upe.loja.repository.entity.Produto;
import java.util.List;
import java.util.ArrayList;

public class ProdutoBusiness implements ProdutoInterface{
  private ProdutoRepository estoque;

  public ProdutoBusiness(ProdutoRepository repository){
    this.estoque = repository; 
  }


  @Override
    public List<Produto> verificarDisponibilidade(String nome){
      List<Produto> listaEstoque = this.estoque.buscarProduto(nome);

      if (listaEstoque.isEmpty()){
        System.out.println("Nenhum produto encontrado!");
        return new ArrayList<>();
      }

      List<Produto> disponiveis = new ArrayList<>();

      for (Produto produto : listaEstoque){
        if (produto.getEstado()){
          disponiveis.add(produto);
        }
      }

      return disponiveis;
    }
}
