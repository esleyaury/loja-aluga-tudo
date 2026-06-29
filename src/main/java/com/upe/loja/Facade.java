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

    public void cadastrarFuncionario(String cpf, String senha, String nome, String email, BigDecimal salario, Cargo cargo){
        funcionarioBusiness.cadastrarFuncionario(cpf, senha, nome, email, salario, cargo);
    }

    public Map<String, Funcionario> listarTodos(){
        return funcionarioBusiness.listarTodos();
    }

    public void atualizar(String cpf, int option, String valor){
        funcionarioBusiness.atualizar(cpf, option, valor);
    }

    public void remover(String cpf){
        funcionarioBusiness.remover(cpf);
    }

    public void fecharPrograma(){
        funcionarioBusiness.guardarDados();
    }
}
