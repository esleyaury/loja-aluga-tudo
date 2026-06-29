package com.upe.loja.repository;

import com.upe.loja.repository.entity.Cliente;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerenciadorClientesCSV {

    // Formato da linha: id;cpf;senha;nome;email;ativo;inadimplente
    private static final int QTD_CAMPOS = 7;

    public Map<String, Cliente> carregar(File arquivoClientes) {
        Map<String, Cliente> listaClientes = new HashMap<>();

        try {
            if (!arquivoClientes.exists()) {
                arquivoClientes.createNewFile();
                return listaClientes;
            }

            List<String> linhas = Files.readAllLines(arquivoClientes.toPath());

            for (String linha : linhas) {
                if (linha.trim().isEmpty()) { continue; }

                String[] dados = linha.split(";");
                if (dados.length != QTD_CAMPOS) { continue; }

                String id = dados[0];
                String cpf = dados[1];
                String senha = dados[2];
                String nome = dados[3];
                String email = dados[4];
                boolean ativo = Boolean.parseBoolean(dados[5]);
                boolean inadimplente = Boolean.parseBoolean(dados[6]);

                Cliente cliente = reconstruirCliente(id, cpf, senha, nome, email, ativo, inadimplente);

                listaClientes.put(cliente.getId(), cliente);
            }
        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }

        return listaClientes;
    }

    // O construtor público de Cliente sempre gera um id novo (UUID), pois no
    // dia a dia o id não é informado por ninguém. Mas ao recarregar do CSV
    // precisamos preservar o id ORIGINAL (senão cada reinício geraria ids
    // diferentes e quebraria as referências), por isso usamos aqui o
    // construtor package-private de Cliente, que aceita o id explicitamente.
    private Cliente reconstruirCliente(String id, String cpf, String senha, String nome,
            String email, boolean ativo, boolean inadimplente) {
        Cliente cliente = new Cliente(id, cpf, senha, nome, email);
        cliente.setAtivo(ativo);
        cliente.setInadimplente(inadimplente);
        return cliente;
    }

    public void guardarDados(File arquivoClientes, Map<String, Cliente> clientes) {
        try (BufferedWriter writer = Files.newBufferedWriter(arquivoClientes.toPath())) {

            for (Cliente c : clientes.values()) {
                String linha = String.format("%s;%s;%s;%s;%s;%s;%s", c.getId(), c.getCpf(),
                    c.getSenha(), c.getNome(), c.getEmail(), c.isAtivo(), c.isInadimplente());

                writer.write(linha);
                writer.newLine();
            }

        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }
    }
}
