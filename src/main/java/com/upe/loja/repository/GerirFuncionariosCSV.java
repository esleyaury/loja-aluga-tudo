package com.upe.loja.repository;

import com.upe.loja.repository.entity.Funcionario;
import com.upe.loja.repository.entity.Funcionario.Cargo;

import java.util.Map;
import java.io.BufferedWriter;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;


public class GerirFuncionariosCSV{
    public Map<String, Funcionario> carregar(File arquivoFuncionarios){
        Map<String, Funcionario> listaFuncionarios = new HashMap<>();

        try{
            if (!arquivoFuncionarios.exists()){
                arquivoFuncionarios.createNewFile();
                return listaFuncionarios;
            }
            List<String> linhas = Files.readAllLines(arquivoFuncionarios.toPath());
        
            for (String linha : linhas){
                if (linha.trim().isEmpty()){continue;}

                String[] dados = linha.split(";");
                if (dados.length != 7){continue;}

                String id = dados[0];
                String cpf = dados[1];
                String senha = dados[2];
                String nome = dados[3];
                String email = dados[4];
                BigDecimal salario = new BigDecimal(dados[5]);
                Cargo cargo = Cargo.valueOf(dados[6].toUpperCase());

                Funcionario funcionario = new Funcionario(cpf,senha,nome,email,salario,cargo);
                listaFuncionarios.put(id, funcionario);
        }

        }catch (Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
        return listaFuncionarios;
    }
    public void guardarDados(File arquivoFuncionarios, Map<String, Funcionario> funcionarios){
        try(BufferedWriter writer = Files.newBufferedWriter(arquivoFuncionarios.toPath())){
            for (Funcionario func : funcionarios.values()){
                String linha = String.format("%s;%s;%s;%s;%s;%s;%s",func.getCpf(),
                func.getCpf(), func.getSenha(), func.getNome(),func.getEmail(),func.getSalario(),
                func.getCargo());

                writer.write(linha);
                writer.newLine();
            }
        }catch (Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
}