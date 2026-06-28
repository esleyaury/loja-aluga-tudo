package com.upe.loja.repository;

import java.io.File;

import com.upe.loja.repository.entity.Cliente;
import com.upe.loja.repository.interfaces.IClienteRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClienteRepository implements IClienteRepository {
    private Map<Long, Cliente> clientes;
    private GerirClientesCSV gerenciadorArquivo;
    private File arquivoClientes;

    public ClienteRepository() {
        this.arquivoClientes = new File("clientes.csv");
        this.gerenciadorArquivo = new GerirClientesCSV();
        this.clientes = gerenciadorArquivo.carregar(this.arquivoClientes);
    }

    public void salvar(Cliente cliente) {
        clientes.put(cliente.getCpf(), cliente);
    }

    public Cliente buscarPorCpf(long cpf) {
        return clientes.get(cpf);
    }

    public Map<Long, Cliente> listarTodos() {
        return new HashMap<>(clientes);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return this.clientes.values().stream()
            .filter(c -> c.getNome().equalsIgnoreCase(nome.trim()))
            .collect(Collectors.toList());
    }

    public void atualizar(Cliente cliente) {
        clientes.put(cliente.getCpf(), cliente);
    }

    public void remover(long cpf) {
        this.clientes.remove(cpf);
    }

    public void guardarDados() {
        gerenciadorArquivo.guardarDados(this.arquivoClientes, this.clientes);
    }
}
