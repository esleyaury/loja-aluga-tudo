package com.upe.loja.repository;

import com.upe.loja.repository.interfaces.IFuncionarioRepository;
import com.upe.loja.repository.entity.Funcionario;
import java.util.HashMap;
import java.util.Map;
import java.io.File; 

public class FuncionarioRepository implements IFuncionarioRepository{
    private Map<String, Funcionario> funcionarios;
    private GerirFuncionariosCSV gerenciadorArquivo;
    private File arquivoFuncionarios;

    public FuncionarioRepository(){
        this.arquivoFuncionarios = new File("funcionarios.csv");
        this.gerenciadorArquivo = new GerirFuncionariosCSV();
        this.funcionarios = gerenciadorArquivo.carregar(this.arquivoFuncionarios);
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
        gerenciadorArquivo.guardarDados(this.arquivoFuncionarios, this.funcionarios);
    }
}