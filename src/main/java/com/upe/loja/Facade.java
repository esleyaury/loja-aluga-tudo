package com.upe.loja;

import java.math.BigDecimal;
import java.util.Map;

import com.upe.loja.business.FuncionarioBusiness;
import com.upe.loja.business.interfaces.IFuncionarioBusiness;
import com.upe.loja.repository.entity.Funcionario;
import com.upe.loja.repository.entity.Funcionario.Cargo;

public class Facade {
    private final IFuncionarioBusiness funcionarioBusiness;

    public Facade(){
        this.funcionarioBusiness = new FuncionarioBusiness();
    }

    public void cadastrarFuncionario(String id, String cpf, String senha, String nome, String email, BigDecimal salario, Cargo cargo){
        funcionarioBusiness.cadastrarFuncionario(id, cpf, senha, nome, email, salario, cargo);
    }

    /*public Funcionario buscarPorId(String id){ //pergunta a winsley
        return funcionarioBusiness.buscarPorId(id);
    } */
    
    public Funcionario buscarPorCpf(String cpf){ //pergunta a winsley
        return funcionarioBusiness.buscarPorCpf(cpf);
    }

    public Map<String, Funcionario> listarTodos(){
        return funcionarioBusiness.listarTodos();
    }

    public void atualizar(String id, int option, String valor){
        funcionarioBusiness.atualizar(id, option, valor);
    }

    public void remover(String id){
        funcionarioBusiness.remover(id);
    }

    public void fecharPrograma(){
        funcionarioBusiness.guardarDados();
    }
}
