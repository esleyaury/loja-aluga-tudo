package com.upe.loja.repository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Cliente;
import com.upe.loja.repository.interfaces.IClienteRepository;

public class ClienteRepository implements IClienteRepository {
    private Map<String, Cliente> clientes;
    private GerirCSV<Cliente> gerenciadorArquivo;
    private File arquivoClientes;

    public ClienteRepository() {
        this.arquivoClientes = new File("clientes.csv");
        this.gerenciadorArquivo = new GerirCSV<>();

        // Formato da linha: cpf;senha;nome;email;ativo;inadimplente
        List<Cliente> lista = gerenciadorArquivo.carregar(this.arquivoClientes, linha -> {
            String[] dados = linha.split(";");
            if (dados.length != 6) { return null; }

            Cliente cliente = new Cliente(dados[0], dados[1], dados[2], dados[3]);
            cliente.setAtivo(Boolean.parseBoolean(dados[4]));
            cliente.setInadimplente(Boolean.parseBoolean(dados[5]));
            return cliente;
        });

        this.clientes = new HashMap<>();
        for (Cliente cliente : lista) {
            this.clientes.put(cliente.getCpf(), cliente);
        }
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
        gerenciadorArquivo.guardarDados(this.arquivoClientes, this.clientes.values(), c ->
            String.format("%s;%s;%s;%s;%s;%s", c.getCpf(), c.getSenha(), c.getNome(),
                c.getEmail(), c.isAtivo(), c.isInadimplente()));
    }
}
