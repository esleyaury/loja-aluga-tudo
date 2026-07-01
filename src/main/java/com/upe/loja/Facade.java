package com.upe.loja;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.upe.loja.business.CategoriaBusiness;
import com.upe.loja.business.ProdutoBusiness;
import com.upe.loja.business.interfaces.ICategoriaBusiness;
import com.upe.loja.business.interfaces.IProdutoBusiness;
import com.upe.loja.repository.entity.Produto;

public class Facade {
    private final IProdutoBusiness produtoBusiness;
    private final ICategoriaBusiness categoriaBusiness;

    public Facade(){
        this.produtoBusiness = new ProdutoBusiness();
        this.categoriaBusiness = new CategoriaBusiness();
    }

    public void cadastrarProduto(String id, String nome, String categoria, BigDecimal taxaDiaria,
        String conservacao, BigDecimal valorReposicao){
        produtoBusiness.cadastrarProduto(id, nome, categoria, taxaDiaria, conservacao, valorReposicao);
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
    
    public void criarCategoria(String nome){
        categoriaBusiness.criarCategoria(nome);
    }

    public Set<String> listarCategorias(){
        return categoriaBusiness.listarCategorias();
    }

    public void deletarCategoria(String nome){
        categoriaBusiness.deletarCategoria(nome);
    }

    public boolean buscarCategoria(String nome){
        return categoriaBusiness.buscarCategoria(nome);
    }

    public void atualizarCategoria(String nomeAntigo, String nomeNovo){
        categoriaBusiness.atualizar(nomeAntigo, nomeNovo);
    }

    public void fecharPrograma(){
        produtoBusiness.guardarDados();
        categoriaBusiness.guardarDados();
    }
}
