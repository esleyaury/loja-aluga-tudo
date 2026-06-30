package com.upe.loja.business;

import com.upe.loja.repository.entity.Fornecedor;
import com.upe.loja.business.interfaces.IFornecedorService;
import java.util.List;

public class FornecedorFacade {
    private IFornecedorService service;

    public FornecedorFacade(IFornecedorService service) {
        this.service = service;
    }

    public void salvar(Fornecedor fornecedor) {
        this.service.salvar(fornecedor);
    }

    public List<Fornecedor> listarTodos() {
        return this.service.listarTodos();
    }

    public void atualizar(Fornecedor fornecedor, int option, String valor) {
        this.service.atualizar(fornecedor, option, valor);
    }

    public void remover(String cnpj) {
        this.service.remover(cnpj);
    }
}