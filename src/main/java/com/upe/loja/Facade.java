package com.upe.loja;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.upe.loja.business.ProdutoBusiness;
import com.upe.loja.business.interfaces.IProdutoBusiness;
import com.upe.loja.repository.entity.Produto;

public class Facade {
    private final IProdutoBusiness produtoBusiness;

    public Facade(){
        this.produtoBusiness = new ProdutoBusiness();
    }

    public void cadastrarProduto(String id, String nome, BigDecimal taxaDiaria,
        String conservacao, BigDecimal valorReposicao){
        produtoBusiness.cadastrarProduto(id, nome, taxaDiaria, conservacao, valorReposicao);
    }

    public List<Produto> verificarDisponibilidade(String nome){
        return produtoBusiness.verificarDisponibilidade(nome);
    }

    public Produto buscarPorId(String id){
        return produtoBusiness.buscarPorId(id);
    }

    public void atualizarProduto(Produto produto, int option, String valor){
        produtoBusiness.atualizar(produto, option, valor);
    }

    public Map<String, Produto> listarTodos(){
        return produtoBusiness.listarTodos();
    }

    public void removerProduto(String id){
        produtoBusiness.remover(id);
    }

    public void fecharPrograma(){
        produtoBusiness.guardarDados();
    }

}
