package com.upe.loja.repository;

import java.util.HashMap;
import java.util.Map;
import java.io.File;

import com.upe.loja.repository.entity.Fornecedor;
import com.upe.loja.repository.interfaces.IFornecedorRepository;

public class FornecedorRepository implements IFornecedorRepository{
    private Map<String, Fornecedor> fornecedores;
    private GerirFornecedoresCSV gerenciadorArquivo;
    private File arquivoFornecedores;

    public void salvar(Fornecedor fornecedor){
        fornecedores.put(fornecedor.getCnpj(),fornecedor);
    }

    public Map<String, Fornecedor> listarTodos(){
        return new HashMap<>(fornecedores);
    }

    public Fornecedor buscarPorCnpj(String cnpj){
        return fornecedores.get(cnpj);
    }

    public void atualizar(Fornecedor fornecedor){
        fornecedores.put(fornecedor.getCnpj(), fornecedor);
    }
    
    public void remover(String cnpj){
        this.fornecedores.remove(cnpj);
    }
    
    public void guardarDados(){
        gerenciadorArquivo.guardarDados(this.arquivoFornecedores, this.fornecedores);
    }
}
