package com.upe.loja.business.interfaces;

import com.upe.loja.repository.entity.Fornecedor;
import java.util.List;

public interface IFornecedorService {
    void salvar(Fornecedor fornecedor);
    List<Fornecedor> listarTodos();
    void atualizar(Fornecedor fornecedor, int option, String valor);
    void remover(String id);
}