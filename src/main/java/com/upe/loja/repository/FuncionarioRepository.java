package com.upe.loja.repository;

import com.upe.loja.repository.interfaces.IFuncionarioRepository;
import com.upe.loja.repository.entity.Funcionario;
import java.util.HashMap;
import java.util.Map;
import java.io.File; 

public class FuncionarioRepository implements IFuncionarioRepository{
    private Map<String, Funcionario> funcionarios;
    private GerirFuncionariosCSV gereciadorArquivo;
    private File arquivoFuncionarios;

    public FuncionarioRepository(){
        this.arquivoFuncionarios = new File("funcionarios.csv");
        this.gereciadorArquivo = new GerirFuncionariosCSV();
        this.funcionarios = gereciadorArquivo.carregar(this.arquivoFuncionarios);
    }
    public void salvar(Funcionario funcionario){
        funcionarios.put(funcionario.getId(), funcionario);
    }
    public Funcionario buscarPorId(String id){
        return funcionarios.get(id);
    }
    public Map<String, Funcionario> listarTodos(){
        return new HashMap<>(funcionarios);
    }
    public Funcionario buscarPorCpf(String cpf){
        return funcionarios.get(cpf);
    }
    public void atualizar(Funcionario funcionario){
        funcionarios.put(funcionario.getId(), funcionario);
    }
    public void remover(String id){
        this.funcionarios.remove(id);
    }
    public void guardarDados(){
        gereciadorArquivo.guardarDados(this.arquivoFuncionarios, this.funcionarios);
    }
}