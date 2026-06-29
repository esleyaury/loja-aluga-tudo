package com.upe.loja.business;

import java.util.Map;
import java.util.Set;

import com.upe.loja.business.interfaces.IFornecedorBusiness;
import com.upe.loja.repository.FornecedorRepository;
import com.upe.loja.repository.entity.Fornecedor;
import com.upe.loja.repository.interfaces.IFornecedorRepository;

public class FornecedorBusiness implements IFornecedorBusiness{
    private IFornecedorRepository fornecedores;

    public FornecedorBusiness(){
        this.fornecedores = new FornecedorRepository();
    }

    @Override
    public void cadastrarFornecedor(String cnpj, String razaoSocial, Set<String> produtos){
        if (fornecedores.buscarPorCnpj(cnpj) != null){
            throw new IllegalArgumentException("CNPJ já existente");
        }
        Fornecedor fornecedor = new Fornecedor(cnpj, razaoSocial, produtos);
        fornecedores.salvar(fornecedor);
    }
    
    @Override
    public void salvar(Fornecedor fornecedor){
        fornecedores.salvar(fornecedor);
    }
    
    @Override
    public Fornecedor buscarPorCnpj(String cnpj){
        return fornecedores.buscarPorCnpj(cnpj);
    }
    
    @Override
    public Map<String, Fornecedor> listarTodos(){
        return fornecedores.listarTodos();
    }
    
    @Override
    public void atualizar(String cnpj, int option, String valor){
        Fornecedor fornecedor = fornecedores.buscarPorCnpj(cnpj);
        if (fornecedor == null){
        throw new IllegalArgumentException("Fornecedor inválido");
        }
        if (valor == null || valor.trim().isEmpty()){
            throw new IllegalArgumentException("Novo valor não pode ser vazio");
        }
        switch (option){
            case 1 -> fornecedor.setRazaoSocial(valor.trim());
            case 2 -> fornecedor.adicionarProduto(valor.trim());
            case 3 -> fornecedor.removerProduto(valor.trim());
        }
    }
    
    @Override
    public void remover(String cnpj){
        Fornecedor fornecedor = fornecedores.buscarPorCnpj(cnpj);
        if(fornecedor == null){
        throw new IllegalArgumentException("Fornecedor não encontrado");
        }
        if (!fornecedor.getProdutos().isEmpty()){
            throw new IllegalArgumentException("Fornecedor possui produtos vinculados");
        }

        fornecedores.remover(cnpj);
    }
    
    @Override
    public void guardarDados(){
        fornecedores.guardarDados();
    }
}
