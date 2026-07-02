package com.upe.loja.repository;

import com.upe.loja.repository.entity.Fornecedor;
import com.upe.loja.repository.interfaces.IFornecedorRepository;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FornecedorRepository implements IFornecedorRepository {
    private GerirFornecedoresCSV gerenciadorArquivo;
    private List<Fornecedor> fornecedores;
    private File arquivoFornecedores;

    public FornecedorRepository() {
        this.arquivoFornecedores = new File("fornecedores.csv");
        this.gerenciadorArquivo= new GerirFornecedoresCSV();
        this.fornecedores = gerenciadorArquivo.carregar(this.arquivoFornecedores);
    }

    public List<Fornecedor> listarTodos() {
        return new ArrayList<>(fornecedores);
    }
   
    public void salvar(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
        guardarDados();
    }

    public void remover(String cnpj) {
        fornecedores.removeIf(f->f.getCnpj().equals(cnpj));
    }

    public void atualizar(Fornecedor fornecedor) {
    }
    @Override
    public void guardarDados(){
        gerenciadorArquivo.guardarDados(this.arquivoFornecedores, this.fornecedores);
    }
}