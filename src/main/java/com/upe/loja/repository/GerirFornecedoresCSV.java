package com.upe.loja.repository;

import com.upe.loja.repository.entity.Fornecedor;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class GerirFornecedoresCSV {

    public List<Fornecedor> carregar(File arquivoFornecedores){
        List<Fornecedor> listaFornecedors = new ArrayList<>();

        try{
            if (!arquivoFornecedores.exists()){
                arquivoFornecedores.createNewFile();
                return listaFornecedors;
            }

            List<String> linhas = Files.readAllLines(arquivoFornecedores.toPath());

            for (String linha : linhas){
                if (linha.trim().isEmpty()){continue;}

                String[] dados = linha.split(";");
                if (dados.length != 3){continue;}

                String cnpj = dados[0];
                String nome = dados[1];
                String telefone = dados[2];

                Fornecedor fornecedor = new Fornecedor(cnpj, nome, telefone);
                listaFornecedors.add(fornecedor);
            }
        } catch (Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
        return listaFornecedors;
    }

    public void guardarDados(File arquivoFonecedores, List<Fornecedor> fornecedores){
        try (BufferedWriter writer = Files.newBufferedWriter(arquivoFonecedores.toPath())){
            for (Fornecedor f : fornecedores){
                String linha = String.format("%s;%s;%s",
                    f.getCnpj(), f.getNome(), f.getTelefone());
                    writer.write(linha);
                    writer.newLine();
            }
        } catch (Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
    } 
}
