package com.upe.loja.repository;

import com.upe.loja.repository.entity.Fornecedor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerirFornecedoresCSV {
    private static final String ARQUIVO = "fornecedores.csv";

    public void salvarArquivoCSV(List<Fornecedor> fornecedores) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Fornecedor f : fornecedores) {
                writer.println(f.getCnpj() + "," + f.getNome() + "," + f.getTelefone());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo CSV: " + e.getMessage());
        }
    }

    public List<Fornecedor> carregarDoCSV() {
        List<Fornecedor> lista = new ArrayList<>();
        File file = new File(ARQUIVO);
        if (!file.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    lista.add(new Fornecedor(partes[0], partes[1], partes[2]));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo CSV: " + e.getMessage());
        }
        return lista;
    }
}