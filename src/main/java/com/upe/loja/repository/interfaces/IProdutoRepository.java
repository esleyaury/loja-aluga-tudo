package com.upe.loja.repository.interfaces;

import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Produto;

public interface IProdutoRepository {
    public void salvar(Produto produto);
    public Produto buscarPorId(String id);
    public Map<String, Produto> listarTodos();
    public List<Produto> buscarProduto(String nome);
    public void atualizar(Produto produto);
    public void remover(String id);
    public void guardarDados();
}
