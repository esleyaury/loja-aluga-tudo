package com.upe.loja.business.interfaces;
import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Produto;

public interface IProdutoBusiness {
    public List<Produto> verificarDisponibilidade(String nome);
    void salvar(Produto produto);
    void atualizar(Produto produto, int option, String valor);
    Map<String, Produto> listarTodos();
    void remover(String id);
    void guardarDados();
}
