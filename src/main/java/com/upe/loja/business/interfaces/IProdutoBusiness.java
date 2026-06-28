package com.upe.loja.business.interfaces;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Produto;

public interface IProdutoBusiness {
    // [CORREÇÃO ANTIGRAVITY] Adicionado método cadastrarProduto que recebe dados
    // primitivos. A criação do objeto Produto é responsabilidade da camada de
    // negócio, não da Facade.
    void cadastrarProduto(String id, String nome, BigDecimal taxaDiaria,
        String conservacao, BigDecimal valorReposicao);
    public List<Produto> verificarDisponibilidade(String nome);
    // [CORREÇÃO ANTIGRAVITY] Método buscarPorId estava faltando na interface
    // embora fosse implementado no ProdutoBusiness e usado pelo Facade.
    Produto buscarPorId(String id);
    void salvar(Produto produto);
    void atualizar(Produto produto, int option, String valor);
    Map<String, Produto> listarTodos();
    void remover(String id);
    void guardarDados();
    public Map<String, Produto> produtosDisponiveis();
}
