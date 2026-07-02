package com.upe.loja.repository.interfaces;

import com.upe.loja.repository.entity.Fornecedor;
import java.util.List;

public interface IFornecedorRepository {
    void salvar(Fornecedor fornecedor);
    List<Fornecedor> listarTodos();
    void atualizar(Fornecedor fornecedor);
    void remover(String cnpj);
    void guardarDados();
}
