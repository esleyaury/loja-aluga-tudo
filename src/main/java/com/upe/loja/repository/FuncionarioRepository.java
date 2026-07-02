package com.upe.loja.repository;

import com.upe.loja.repository.interfaces.IFuncionarioRepository;
import com.upe.loja.repository.entity.Funcionario;
import com.upe.loja.repository.entity.Funcionario.Cargo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;

public class FuncionarioRepository implements IFuncionarioRepository{
    private Map<String, Funcionario> funcionarios;
    private GerirCSV<Funcionario> gerenciadorArquivo;
    private File arquivoFuncionarios;

    public FuncionarioRepository(){
        this.arquivoFuncionarios = new File("funcionarios.csv");
        this.gerenciadorArquivo = new GerirCSV<>();

        // Formato da linha: cpf;senha;nome;email;salario;cargo
        List<Funcionario> lista = gerenciadorArquivo.carregar(this.arquivoFuncionarios, linha -> {
            String[] dados = linha.split(";");
            if (dados.length != 6) { return null; }

            BigDecimal salario = new BigDecimal(dados[4]);
            Cargo cargo = Cargo.valueOf(dados[5].toUpperCase());
            return new Funcionario(dados[0], dados[1], dados[2], dados[3], salario, cargo);
        });

        this.funcionarios = new HashMap<>();
        for (Funcionario funcionario : lista) {
            this.funcionarios.put(funcionario.getCpf(), funcionario);
        }
    }
    public void salvar(Funcionario funcionario){
        funcionarios.put(funcionario.getCpf(), funcionario);
    }

    public Map<String, Funcionario> listarTodos(){
        return new HashMap<>(funcionarios);
    }
    public Funcionario buscarPorCpf(String cpf){
        return funcionarios.get(cpf);
    }
    public void atualizar(Funcionario funcionario){
        funcionarios.put(funcionario.getCpf(), funcionario);
    }
    public void remover(String cpf){
        this.funcionarios.remove(cpf);
    }
    public void guardarDados(){
        gerenciadorArquivo.guardarDados(this.arquivoFuncionarios, this.funcionarios.values(), f ->
            String.format("%s;%s;%s;%s;%s;%s", f.getCpf(), f.getSenha(), f.getNome(),
                f.getEmail(), f.getSalario(), f.getCargo()));
    }
}
