package com.upe.loja;

import com.upe.loja.business.FornecedorBusiness;
import com.upe.loja.business.interfaces.IFornecedorBusiness;
import com.upe.loja.repository.entity.Fornecedor;

import java.util.Map;
import java.util.Set;

public class Facade {
    private final IFornecedorBusiness fornecedorBusiness;

    public Facade(){
        this.fornecedorBusiness = new FornecedorBusiness();
    }

    public void cadastrarFornecedor(String cnpj, String razaoSocial, Set<String> produtos){
        fornecedorBusiness.cadastrarFornecedor(cnpj, razaoSocial, produtos);
    }

    public Map<String, Fornecedor> listarTodos(){
        return fornecedorBusiness.listarTodos();
    }

    public void atualizar(String cnpj, int option, String valor){
        fornecedorBusiness.atualizar(cnpj, option, valor);
    }

    public void remover(String cnpj){
        fornecedorBusiness.remover(cnpj);
    }

    public void fecharPrograma(){
        fornecedorBusiness.guardarDados();
    }
}
