package com.upe.loja.business.interfaces;

import com.upe.loja.repository.entity.Fornecedor;

import java.util.Map;
import java.util.Set;

public interface IFornecedorBusiness {
    public void cadastrarFornecedor(String cnpj, String razaoSocial, Set<String> produtos);
    public void salvar();
    public Fornecedor buscarPorCnpj();
    public Map<String, Fornecedor> listarTodos();
    public void atualizar();
    public void remover();
    public void guardarDados();
}
