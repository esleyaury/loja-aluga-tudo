package com.upe.loja;
import java.math.BigDecimal;
import java.util.List;
import com.upe.loja.business.*;
import com.upe.loja.repository.entity.*;

public class Facade {
    private final ProdutoBusiness produtoBusiness;

    public Facade (ProdutoBusiness produtoBusiness){
        this.produtoBusiness = produtoBusiness;
    }

    public void cadastrarProduto(String id, String nome, BigDecimal taxaDiaria,
        String conservacao, BigDecimal valorReposicao){
        // Todo o processo de cadastrar um produto.
        // Criar um novo produto
        Produto produto = new Produto(id, nome, taxaDiaria, conservacao,
            valorReposicao, Produto.EstadoProduto.valueOf("DISPONIVEL"));
        produtoBusiness.salvar(produto);

        // settar as informaçoes dele
        // salvar nele no csv
    }

    // Isso vai precisar retornar 
    public List<Produto> verificarDisponibilidade(String nome){
        return produtoBusiness.verificarDisponibilidade(nome);
    }

    public void atualizarProduto(Produto produto, int option, String valor){
        produtoBusiness.atualizar(produto, option, valor);
    }

    public List<Produto> listarTodos(){
        return produtoBusiness.listarTodos();
    }

    public void removerProduto(String id){
        produtoBusiness.remover(id);
    }

    public void fecharPrograma(){
        produtoBusiness.guardarDados(); 
        //se tiver fechamento de mais algo coloca aq junto
    }
    
}
