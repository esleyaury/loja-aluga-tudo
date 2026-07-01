package com.upe.loja.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Ocorrencia;
import com.upe.loja.repository.entity.Ocorrencia.TipoOcorrencia;

public class GerirOcorrenciasCSV {

    // Formato da linha:
    // idContrato;tipo;dataOcorrencia;descricao;valorMulta;quitada

    public Map<Long, Ocorrencia> carregar(File arquivo) {
        Map<Long, Ocorrencia> ocorrencias = new HashMap<>();

        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
                return ocorrencias;
            }

            List<String> linhas = Files.readAllLines(arquivo.toPath());

            for (String linha : linhas) {
                if (linha.trim().isEmpty()) { continue; }

                String[] dados = linha.split(";");
                if (dados.length < 6) { continue; }

                long idContrato       = Long.parseLong(dados[0]);
                TipoOcorrencia tipo   = TipoOcorrencia.valueOf(dados[1]);
                LocalDate data        = LocalDate.parse(dados[2]);
                String descricao      = dados[3];
                BigDecimal valorMulta = new BigDecimal(dados[4]);
                boolean quitada       = Boolean.parseBoolean(dados[5]);

                Ocorrencia ocorrencia = new Ocorrencia(idContrato, tipo, data, descricao, valorMulta, quitada);
                ocorrencias.put(idContrato, ocorrencia);
            }
        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }

        return ocorrencias;
    }

    public void guardarDados(File arquivo, Map<Long, Ocorrencia> ocorrencias) {
        try (BufferedWriter writer = Files.newBufferedWriter(arquivo.toPath())) {

            for (Ocorrencia o : ocorrencias.values()) {
                String linha = String.format("%d;%s;%s;%s;%s;%b",
                        o.getIdContrato(),
                        o.getTipo(),
                        o.getDataOcorrencia(),
                        o.getDescricao(),
                        o.getValorMulta(),
                        o.isQuitada());

                writer.write(linha);
                writer.newLine();
            }

        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }
    }
}