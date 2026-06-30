package com.upe.loja.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GerirCategoriasCSV {
    public Set<String> carregar(File arquivoCategorias) {
        Set<String> categorias = new HashSet<>();
 
        try {
            if (!arquivoCategorias.exists()) {
                arquivoCategorias.createNewFile();
                return categorias;
            }
 
            List<String> linhas = Files.readAllLines(arquivoCategorias.toPath());
 
            for (String linha : linhas) {
                if (!linha.trim().isEmpty()) {
                    categorias.add(linha.trim().toLowerCase());
                }
            }
        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }
 
        return categorias;
    }
 
    public void guardarDados(File arquivoCategorias, Set<String> categorias) {
        try (BufferedWriter writer = Files.newBufferedWriter(arquivoCategorias.toPath())) {
            for (String nome : categorias) {
                writer.write(nome);
                writer.newLine();
            }
        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
        }
    }
}
