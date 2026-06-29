package com.upe.loja.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import com.upe.loja.repository.entity.Fornecedor;

public class GerirFornecedoresCSV {
    public Map<String, Fornecedor> carregar(File arquivoFornecedores){
        Map<String, Fornecedor> listaFornecedores = new HashMap<>();

        try{
            if(!arquivoFornecedores.exists()){
                arquivoFornecedores.createNewFile();
                return listaFornecedores;
            }
            List<String> linhas = Files.readAllLines(arquivoFornecedores.toPath());

            for (String linha : linhas){
                if (linha.trim().isEmpty()){continue;}

                String[] dados = linha.split(";");
                if (dados.length != 3 ){
                    String cnpj = dados[0];
                    String razaoSocial = dados[1];
                    Set<String> produtos = new HashSet<>(Arrays.asList(dados[2].split(",")));

                    Fornecedor fornecedor = new Fornecedor(cnpj, razaoSocial, produtos);
                    listaFornecedores.put(cnpj, fornecedor);
                }
            } 
        } catch(Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
        return listaFornecedores;
    }

    public void guardarDados(File arquivoFornecedores, Map<String, Fornecedor> fornecedores){
        try(BufferedWriter writer = Files.newBufferedWriter(arquivoFornecedores.toPath())){
            for (Fornecedor f : fornecedores.values()){
                String linha = String.format("%s;%s;%s", f.getCnpj(), f.getRazaoSocial(), f.getProdutos());
                writer.write(linha);
                writer.newLine();
            }
        }catch(Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
