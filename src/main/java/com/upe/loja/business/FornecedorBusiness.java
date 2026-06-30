package com.upe.loja.business;

import com.upe.loja.repository.FornecedorRepository;
import com.upe.loja.repository.entity.Fornecedor;
import com.upe.loja.business.interfaces.IFornecedorService;
import java.util.List;

public class FornecedorBusiness implements IFornecedorService {
    private FornecedorRepository repository;

    public FornecedorBusiness(FornecedorRepository repository) {
        this.repository = repository;
    }

    public void salvar(Fornecedor fornecedor) {
        if (fornecedor.getCnpj() == null || fornecedor.getCnpj().isEmpty()) {
            throw new IllegalArgumentException("CNPJ do fornecedor não pode ser vazio.");
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

    public void remover(String cnpj) {
        this.repository.remover(cnpj);
    }
}