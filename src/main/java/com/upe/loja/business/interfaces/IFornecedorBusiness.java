package com.upe.loja.business.interfaces;

import com.upe.loja.repository.entity.Fornecedor;
import java.util.List;

public interface IFornecedorBusiness {
    void salvar(Fornecedor fornecedor);
    List<Fornecedor> listarTodos();
    void atualizar(String cnpj, int opcao, String novoValor);
    void remover(String cnpj);
    void guardarDados();
}
