package com.upe.loja.repository;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Manipulador genérico de persistência em CSV, reutilizado por todos os
 * repositórios. Cada repositório fornece um parser (linha -> entidade) e um
 * serializer (entidade -> linha); a leitura/escrita do arquivo em si é
 * idêntica para todas as entidades.
 */
public class GerirCSV<T> {

    public List<T> carregar(File arquivo, Function<String, T> parser) {
        List<T> itens = new ArrayList<>();

        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
                return itens;
            }

            List<String> linhas = Files.readAllLines(arquivo.toPath());

            for (String linha : linhas) {
                if (linha.trim().isEmpty()) { continue; }

                T item = parser.apply(linha);
                if (item != null) {
                    itens.add(item);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

        return itens;
    }

    public void guardarDados(File arquivo, Iterable<T> itens, Function<T, String> serializer) {
        try (BufferedWriter writer = Files.newBufferedWriter(arquivo.toPath())) {
            for (T item : itens) {
                writer.write(serializer.apply(item));
                writer.newLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
