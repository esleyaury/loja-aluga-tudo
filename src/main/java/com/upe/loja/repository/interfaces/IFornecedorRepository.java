package com.upe.loja.repository.interfaces;

import java.util.Map;
import com.upe.loja.repository.entity.*;;

public interface IFornecedorRepository {
    public void salvar(Fornecedor fornecedor);
    public Map<String, Fornecedor> listarTodos();
    public Fornecedor buscarPorCnpj(String cnpj);
    public void atualizar(Fornecedor fornecedor);
    public void remover(String cnpj);
    public void guardarDados();
}
