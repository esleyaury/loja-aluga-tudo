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
        
        // Regra: Não permitir salvar se o CNPJ já estiver cadastrado no HashMap
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
    public void atualizar(Fornecedor fornecedor, int option, String valor) {
        switch (option) {
            case 1: 
                fornecedor.setNome(valor); 
                break;
            case 2: 
                fornecedor.setCnpj(valor); 
                break;
            case 3: 
                fornecedor.setTelefone(valor); 
                break;
            default: 
                throw new IllegalArgumentException("Opção inválida.");
        }
        this.repository.atualizar(fornecedor);
    }

    @Override
    public void remover(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ inválido para remoção.");
        }
        this.repository.remover(cnpj);
    }
    
    // Método para ser repassado ao menu principal e fechar a execução
    public void salvarDadosAntesDeSair() {
        this.repository.salvarArquivoCSV();
    }
}