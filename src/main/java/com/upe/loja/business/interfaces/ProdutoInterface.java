package com.upe.loja.business.interfaces;
import java.util.List;

import com.upe.loja.repository.entity.Produto;

public interface ProdutoInterface {
    public List<Produto> verificarDisponibilidade(String nome);
    // void salvar(Produto produto);
    // void atualizar(Produto produto, int option, String valor);
    // List<Produto> listarTodos();
    // void remover(String id);
}
