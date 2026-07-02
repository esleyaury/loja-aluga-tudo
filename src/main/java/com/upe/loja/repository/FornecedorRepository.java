package com.upe.loja.repository;

import com.upe.loja.repository.entity.Fornecedor;
import com.upe.loja.repository.interfaces.IFornecedorRepository;
import java.util.List;

public class FornecedorRepository implements IFornecedorRepository {
    private GerirFornecedoresCSV gerenciadorCSV;
    private List<Fornecedor> fornecedores;

    public FornecedorRepository() {
        this.gerenciadorCSV = new GerirFornecedoresCSV();
        this.fornecedores = gerenciadorCSV.carregarDoCSV();
    }

    @Override
    public List<Fornecedor> listarTodos() {
        return fornecedores;
    }

    @Override
    public void salvar(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
        gerenciadorCSV.salvarArquivoCSV(fornecedores);
    }

    @Override
    public void remover(String cnpj) {
        fornecedores.removeIf(f -> f.getCnpj().equals(cnpj));
        gerenciadorCSV.salvarArquivoCSV(fornecedores);
    }

    @Override
    public void atualizar(Fornecedor fornecedor) {
        gerenciadorCSV.salvarArquivoCSV(fornecedores);
    }

    @Override
    public void guardarDados() {
        gerenciadorCSV.salvarArquivoCSV(fornecedores);
    }
}