package com.upe.loja;

import java.util.List;
import java.util.Map;

import com.upe.loja.business.ClienteBusiness;
import com.upe.loja.business.interfaces.IClienteBusiness;
import com.upe.loja.repository.entity.Cliente;

public class Facade {
    private final IClienteBusiness clienteBusiness;

    public Facade() {
        this.clienteBusiness = new ClienteBusiness();
    }

    public void cadastrarCliente(String cpf, String senha, String nome, String email) {
        clienteBusiness.cadastrarCliente(cpf, senha, nome, email);
    }

    public Cliente buscarPorId(String id) {
        return clienteBusiness.buscarPorId(id);
    }

    public Cliente buscarPorCpf(String cpf) {
        return clienteBusiness.buscarPorCpf(cpf);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteBusiness.buscarPorNome(nome);
    }

    public void atualizarCliente(Cliente cliente, int option, String valor) {
        clienteBusiness.atualizar(cliente, option, valor);
    }

    public Map<String, Cliente> listarTodos() {
        return clienteBusiness.listarTodos();
    }

    public void removerCliente(String id) {
        clienteBusiness.remover(id);
    }

    public boolean podeAlugar(String cpf) {
        return clienteBusiness.podeAlugar(cpf);
    }

    public void fecharPrograma() {
        clienteBusiness.guardarDados();
    }
}
