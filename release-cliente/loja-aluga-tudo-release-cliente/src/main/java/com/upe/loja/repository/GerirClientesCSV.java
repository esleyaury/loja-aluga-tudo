package com.upe.loja.repository;

import com.upe.loja.repository.entity.Cliente;
import com.upe.loja.repository.entity.Cliente.EstadoCliente;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerirClientesCSV {

    // Formato da linha: cpf;senha;nome;telefone;email;estado;inadimplente
    private static final int QTD_CAMPOS = 7;

    public Map<Long, Cliente> carregar(File arquivoClientes) {
        Map<Long, Cliente> listaClientes = new HashMap<>();

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

                long cpf = Long.parseLong(dados[0]);
                String senha = dados[1];
                String nome = dados[2];
                long telefone = Long.parseLong(dados[3]);
                String email = dados[4];
                EstadoCliente estado = EstadoCliente.valueOf(dados[5].toUpperCase());
                boolean inadimplente = Boolean.parseBoolean(dados[6]);

                Cliente cliente = new Cliente(cpf, senha, nome, telefone, email);
                cliente.setEstado(estado);
                cliente.setInadimplente(inadimplente);

                listaClientes.put(cpf, cliente);
            }
        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }

        return listaClientes;
    }

    public void guardarDados(File arquivoClientes, Map<Long, Cliente> clientes) {
        try (BufferedWriter writer = Files.newBufferedWriter(arquivoClientes.toPath())) {

            for (Cliente c : clientes.values()) {
                String linha = String.format("%s;%s;%s;%s;%s;%s;%s", c.getCpf(), c.getSenha(),
                    c.getNome(), c.getTelefone(), c.getEmail(), c.getEstado(), c.isInadimplente());

                writer.write(linha);
                writer.newLine();
            }

        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }
    }
}
