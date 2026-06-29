package com.upe.loja.repository;

import com.upe.loja.repository.entity.Fornecedor;
import java.util.ArrayList;
import java.util.List;

public class FornecedorRepository {
    // Simulação de banco de dados em memória
    private List<Fornecedor> fornecedores = new ArrayList<>();

    public void salvar(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
    }

    public List<Fornecedor> listarTodos() {
        return new ArrayList<>(fornecedores);
    }

    public void salvarAtualizado(Fornecedor fornecedor) {
        // Lógica de atualização (no caso de JSON, seria reescrever o arquivo)
    }

    public void remover(String id) {
        fornecedores.removeIf(f -> f.getID().equals(id));
    }
}