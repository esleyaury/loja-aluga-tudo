package com.upe.loja.repository;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.upe.loja.repository.entity.Cliente;
import com.upe.loja.repository.interfaces.IClienteRepository;

public class ClienteRepository implements IClienteRepository {
    private Map<String, Cliente> clientes;
    private GerenciadorClientesCSV gerenciadorArquivo;
    private File arquivoClientes;

    public ClienteRepository() {
        this.arquivoClientes = new File("clientes.csv");
        this.gerenciadorArquivo = new GerenciadorClientesCSV();
        this.clientes = gerenciadorArquivo.carregar(this.arquivoClientes);
    }

    public void salvar(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }

    public Cliente buscarPorId(String id) {
        return clientes.get(id);
    }

    public Cliente buscarPorCpf(String cpf) {
        return clientes.values().stream()
            .filter(c -> c.getCpf().equals(cpf))
            .findFirst()
            .orElse(null);
    }

    public Map<String, Cliente> listarTodos() {
        return new HashMap<>(clientes);
    }

    public void atualizar(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }

    public void remover(String id) {
        this.clientes.remove(id);
    }

    public void guardarDados() {
        gerenciadorArquivo.guardarDados(this.arquivoClientes, this.clientes);
    }
}
