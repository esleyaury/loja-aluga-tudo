package com.upe.loja.business.interfaces;

import com.upe.loja.repository.entity.Fornecedor;

import java.util.Map;
import java.util.Set;

public interface IFornecedorBusiness {
    public void cadastrarFornecedor(String cnpj, String razaoSocial, Set<String> produtos);
    public void salvar(Fornecedor fornecedor);
    public Fornecedor buscarPorCnpj(String cnpj);
    public Map<String, Fornecedor> listarTodos();
    public void atualizar(String cnpj, int option, String valor);
    public void remover(String cnpj);
    public void guardarDados();
}
