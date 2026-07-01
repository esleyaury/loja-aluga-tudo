package com.upe.loja.repository;

import com.upe.loja.repository.entity.Fornecedor;
import java.io.*;
import java.util.*;

public class FornecedorRepository implements IFornecedorRepository {
    private Map<String, Fornecedor> fornecedoresMap;
    private final String ARQUIVO_CSV = "gerirfornecedores.csv";

    public FornecedorRepository() {
        this.fornecedoresMap = new HashMap<>();
        this.carregarDoCSV();
    }

    private void carregarDoCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CSV))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 3) {
                    // Esperado: CNPJ, Nome, Telefone
                    Fornecedor fornecedor = new Fornecedor(dados[0], dados[1], dados[2]);
                    fornecedoresMap.put(fornecedor.getCnpj(), fornecedor);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de fornecedores não encontrado. Um novo será criado ao finalizar.");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
    }

    @Override
    public void salvar(Fornecedor fornecedor) {
        // Altera apenas no HashMap
        fornecedoresMap.put(fornecedor.getCnpj(), fornecedor);
    }

    @Override
    public List<Fornecedor> listarTodos() {
        // Retorna os valores do mapa como uma lista
        return new ArrayList<>(fornecedoresMap.values());
    }

    @Override
    public void atualizar(Fornecedor fornecedor) {
        // No HashMap, colocar uma chave que já existe sobrescreve o valor antigo
        fornecedoresMap.put(fornecedor.getCnpj(), fornecedor);
    }

    @Override
    public void remover(String cnpj) {
        // Remove do HashMap
        fornecedoresMap.remove(cnpj);
    }

    @Override
    public void salvarArquivoCSV() {
        // Chamado no fim do programa: grava o estado atual do HashMap no CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_CSV))) {
            for (Fornecedor f : fornecedoresMap.values()) {
                bw.write(f.getCnpj() + "," + f.getNome() + "," + f.getTelefone());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo CSV: " + e.getMessage());
        }
    }
}