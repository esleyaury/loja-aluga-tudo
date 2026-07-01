package com.upe.loja.business;

import com.upe.loja.business.interfaces.IFornecedorService;
import com.upe.loja.repository.entity.Fornecedor;

import java.util.List;

public class FornecedorFacade {
    
    private IFornecedorService service;

    public FornecedorFacade(IFornecedorService service) {
        this.service = service;
    }

    public void salvar(Fornecedor fornecedor) {
        service.salvar(fornecedor);
    }

    public List<Fornecedor> listarTodos() {
        return service.listarTodos();
    }

    public void atualizar(String cnpj, int opcao, String novoValor) {
        service.atualizar(cnpj, opcao, novoValor);
    }

    public void remover(String cnpj) {
        service.remover(cnpj);
    }
}