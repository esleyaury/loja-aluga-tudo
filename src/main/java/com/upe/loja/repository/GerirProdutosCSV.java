package com.upe.loja.repository;

import com.upe.loja.repository.entity.Produto;
import com.upe.loja.repository.entity.Produto.EstadoProduto;

import java.io.BufferedWriter;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerirProdutosCSV {
    public Map<String, Produto> carregar(File arquivoProdutos) {
        Map<String, Produto> listaProdutos = new HashMap<>();
        
        try {
            if (!arquivoProdutos.exists()) {
                arquivoProdutos.createNewFile();
                return listaProdutos;
            }
            
            List<String> linhas = Files.readAllLines(arquivoProdutos.toPath());
            
            for (String linha : linhas) {
                if (linha.trim().isEmpty()) {continue;}
                
                String[] dados = linha.split(";");
                if (dados.length != 7) {continue;}
                
                String id = dados[0];
                String nome = dados[1];
                String categoria = dados[2];
                BigDecimal taxaDiaria = new BigDecimal(dados[3]);
                String conservacao = dados[4];
                BigDecimal valorReposicao = new BigDecimal(dados[5]);
                
                EstadoProduto estado = EstadoProduto.valueOf(dados[6].toUpperCase()); 
                
                Produto produto = new Produto(id, nome, categoria, taxaDiaria, conservacao, valorReposicao, estado);
                listaProdutos.put(id, produto);
            }
        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }
        
        return listaProdutos;
    }

    public void guardarDados(File arquivoProdutos, Map<String, Produto> estoque) {
        try (BufferedWriter writer = Files.newBufferedWriter(arquivoProdutos.toPath())) {
            
            for (Produto p : estoque.values()) {
                String linha = String.format("%s;%s;%s;%s;%s;%s;%s", p.getID(), p.getNome(), p.getCategoria(), p.getTaxaDiaria(), 
                    p.getConservacao(), p.getValorReposicao(), p.getEstado());
                
                writer.write(linha);
                writer.newLine();
            }
            
        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }
    }
}
