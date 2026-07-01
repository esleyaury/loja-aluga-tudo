package com.upe.loja.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Contrato.StatusContrato;

public class GerirContratosCSV {

    // Formato da linha:
    // id;cpfCliente;cpfFuncionario;idsProdutos(,separados);dataRetirada;dataDevolucaoPrevista;dataDevolucaoReal;valorTotal;status

    public Map<Long, Contrato> carregar(File arquivo) {
        Map<Long, Contrato> contratos = new HashMap<>();

        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
                return contratos;
            }

            List<String> linhas = Files.readAllLines(arquivo.toPath());

            for (String linha : linhas) {
                if (linha.trim().isEmpty()) { continue; }

                String[] dados = linha.split(";");
                if (dados.length < 9) { continue; }

                long id                        = Long.parseLong(dados[0]);
                String cpfCliente              = dados[1];
                String cpfFuncionario          = dados[2];
                Set<String> idsProdutos        = new HashSet<>(Arrays.asList(dados[3].split(",")));
                LocalDate dataRetirada         = LocalDate.parse(dados[4]);
                LocalDate dataDevolucaoPrevista = LocalDate.parse(dados[5]);
                LocalDate dataDevolucaoReal    = dados[6].equals("null") ? null : LocalDate.parse(dados[6]);
                BigDecimal valorTotal          = new BigDecimal(dados[7]);
                StatusContrato status          = StatusContrato.valueOf(dados[8]);

                Contrato contrato = new Contrato(id, cpfCliente, cpfFuncionario, idsProdutos,
                        dataRetirada, dataDevolucaoPrevista, dataDevolucaoReal, valorTotal, status);

                contratos.put(id, contrato);
            }
        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }

        return contratos;
    }

    public void guardarDados(File arquivo, Map<Long, Contrato> contratos) {
        try (BufferedWriter writer = Files.newBufferedWriter(arquivo.toPath())) {

            for (Contrato c : contratos.values()) {
                String produtos        = String.join(",", c.getIdsProdutos());
                String devolucaoReal   = c.getDataDevolucaoReal() == null ? "null" : c.getDataDevolucaoReal().toString();

                String linha = String.format("%d;%s;%s;%s;%s;%s;%s;%s;%s",
                        c.getId(),
                        c.getCpfCliente(),
                        c.getCpfFuncionario(),
                        produtos,
                        c.getDataRetirada(),
                        c.getDataDevolucaoPrevista(),
                        devolucaoReal,
                        c.getValorTotal(),
                        c.getStatus());

                writer.write(linha);
                writer.newLine();
            }

        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }
    }
}