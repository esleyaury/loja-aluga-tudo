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

    public void cadastrarCliente(long cpf, String senha, String nome, long telefone, String email) {
        clienteBusiness.cadastrarCliente(cpf, senha, nome, telefone, email);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteBusiness.buscarPorNome(nome);
    }

    public Cliente buscarPorCpf(long cpf) {
        return clienteBusiness.buscarPorCpf(cpf);
    }

    public void atualizarCliente(Cliente cliente, int option, String valor) {
        clienteBusiness.atualizar(cliente, option, valor);
    }

    public Map<Long, Cliente> listarTodos() {
        return clienteBusiness.listarTodos();
    }

    public void removerCliente(long cpf) {
        clienteBusiness.remover(cpf);
    }

    public boolean podeAlugar(long cpf) {
        return clienteBusiness.podeAlugar(cpf);
    }

    public void fecharPrograma() {
        clienteBusiness.guardarDados();
    }

}
