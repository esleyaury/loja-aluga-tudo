package com.upe.loja.business;

import com.upe.loja.business.interfaces.IFornecedorService;
import com.upe.loja.repository.FornecedorRepository;
import com.upe.loja.repository.interfaces.IFornecedorRepository;
import com.upe.loja.repository.entity.Fornecedor;
import java.util.List;

public class FornecedorBusiness implements IFornecedorService {
    private IFornecedorRepository repository;

    public FornecedorBusiness() {
        this.repository = new FornecedorRepository();
    }

    @Override
    public void salvar(Fornecedor fornecedor) {
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
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não localizado."));

        switch(opcao){
            case 1 -> fornecedor.setNome(novoValor);
            case 2 -> fornecedor.setCnpj(novoValor);
            case 3 -> fornecedor.setTelefone(novoValor);
            default -> throw new IllegalArgumentException("Opção de atualização inválida.");
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

    @Override
    public void guardarDados(){
        repository.guardarDados();
    }
}