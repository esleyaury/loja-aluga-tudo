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
        clientes.put(cliente.getCpf(), cliente);
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
        clientes.put(cliente.getCpf(), cliente);
    }

    public void remover(String cpf) {
        this.clientes.remove(cpf);
    }

    public void guardarDados() {
        gerenciadorArquivo.guardarDados(this.arquivoClientes, this.clientes);
    }
}
