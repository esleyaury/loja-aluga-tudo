package com.upe.loja.repository;

import com.upe.loja.repository.entity.Fornecedor;
import java.util.List;

public interface IFornecedorRepository {
    void salvar(Fornecedor fornecedor);
    List<Fornecedor> listarTodos();
    void atualizar(Fornecedor fornecedor);
    void remover(String cnpj);
    void salvarArquivoCSV(); // Método para ser chamado apenas no final do programa
}