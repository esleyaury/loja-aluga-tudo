package com.upe.loja.business;

// Tudo nesse Facade, vai entrar em Facade.java, cada entidade n tem seu proprio facade.
import com.upe.loja.repository.FornecedorRepository;
import com.upe.loja.repository.entity.Fornecedor;
import java.util.List;

public class FornecedorFacade {
    private FornecedorBusiness fornecedorBusiness;

    public FornecedorFacade() {
        // Inicializa a camada de negócio já injetando o repositório, mantendo o padrão
        this.fornecedorBusiness = new FornecedorBusiness(new FornecedorRepository());
    }

    public void cadastrarFornecedor(String id, String nome, String cnpj, String telefone) {
        Fornecedor fornecedor = new Fornecedor(id, nome, cnpj, telefone);
        this.fornecedorBusiness.salvar(fornecedor);
    }

    public List<Fornecedor> listarFornecedores() {
        return this.fornecedorBusiness.listarTodos();
    }

    public void atualizarFornecedor(Fornecedor fornecedor, int option, String valor) {
        this.fornecedorBusiness.atualizar(fornecedor, option, valor);
    }

    public void removerFornecedor(String id) {
        this.fornecedorBusiness.remover(id);
    }
}