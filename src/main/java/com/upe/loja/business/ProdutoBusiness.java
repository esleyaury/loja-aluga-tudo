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
        return new ArrayList<>();
      }

      List<Produto> disponiveis = new ArrayList<>();

      for (Produto produto : listaEstoque){
        if (produto.getEstado() == Produto.EstadoProduto.DISPONIVEL){//ignorar case e acento ver isso
          disponiveis.add(produto);
        }
      }
      return disponiveis;
    }

    public void salvar(Produto produto){
      //se for colocar coisa de ah nome nao pode estar vazio e n sei oq bota aq

      estoque.salvar(produto);
    }

    public void atualizar(Produto produto, int option, String valor){
      //colocar parte de verificacao aqui pra throw exception e tal

      estoque.atualizar(produto, option, valor);
    }

    public List<Produto> listarTodos(){
      //verificacoes aq
      return estoque.listarTodos();
    }

    public void remover(String id){
      if (estoque.buscarPorId(id).isEmpty()){
        throw new IllegalArgumentException("Produto não encontrado"); //não é print
      }
      estoque.remover(id);
    }
}
