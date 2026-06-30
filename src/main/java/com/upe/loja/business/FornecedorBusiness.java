package com.upe.loja.business;

import com.upe.loja.repository.FornecedorRepository;
import com.upe.loja.repository.entity.Fornecedor;
import java.util.List;
import com.upe.loja.business.interfaces.IFornecedorService;

public class FornecedorBusiness implements IFornecedorService {
    private FornecedorRepository repository;

    public FornecedorBusiness(FornecedorRepository repository) {
        this.repository = repository;
    }

    public void salvar(Fornecedor fornecedor) {
        if (fornecedor.getID() == null || fornecedor.getID().isEmpty()) {
            throw new IllegalArgumentException("ID do fornecedor não pode ser vazio.");
        }
        this.repository.salvar(fornecedor);
    }

    public List<Fornecedor> listarTodos() {
        return this.repository.listarTodos();
    }

    public void atualizar(Fornecedor fornecedor, int option, String valor) {
        switch (option) {
            case 1: fornecedor.setNome(valor); break;
            case 2: fornecedor.setCnpj(valor); break;
            case 3: fornecedor.setTelefone(valor); break;
            default: throw new IllegalArgumentException("Opção inválida.");
        }
        this.repository.salvarAtualizado(fornecedor);
    }

    public void remover(String id) {
        this.repository.remover(id);
    }
}