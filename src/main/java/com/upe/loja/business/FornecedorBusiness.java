package com.upe.loja.business;

import com.upe.loja.repository.IFornecedorRepository;
import com.upe.loja.repository.entity.Fornecedor;
import com.upe.loja.business.interfaces.IFornecedorService;
import java.util.List;

public class FornecedorBusiness implements IFornecedorService {
    private IFornecedorRepository repository;

    public FornecedorBusiness(IFornecedorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void salvar(Fornecedor fornecedor) {
        if (fornecedor.getCnpj() == null || fornecedor.getCnpj().trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ do fornecedor não pode ser vazio.");
        }
        
        // Regra: Não permitir salvar se o CNPJ já estiver cadastrado no banco/lista
        List<Fornecedor> existentes = this.repository.listarTodos();
        for (Fornecedor f : existentes) {
            if (f.getCnpj().equals(fornecedor.getCnpj())) {
                throw new IllegalArgumentException("Já existe um fornecedor com este CNPJ.");
            }
        }

        this.repository.salvar(fornecedor);
    }

    @Override
    public List<Fornecedor> listarTodos() {
        return this.repository.listarTodos();
    }

    @Override
    public void atualizar(String cnpj, int opcao, String novoValor) {
        Fornecedor fornecedor = repository.listarTodos().stream()
                .filter(f -> f.getCnpj().equals(cnpj))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não localizado com o CNPJ informado."));

        if (opcao == 1) {
            fornecedor.setNome(novoValor);
        } else if (opcao == 2) {
            fornecedor.setCnpj(novoValor);
        } else if (opcao == 3) {
            fornecedor.setTelefone(novoValor);
        } else {
            throw new IllegalArgumentException("Opção de atualização inválida.");
        }
        repository.atualizar(fornecedor);
    }

    @Override
    public void remover(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ inválido para remoção.");
        }
        this.repository.remover(cnpj);
    }
}