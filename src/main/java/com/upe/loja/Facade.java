package com.upe.loja;

import java.util.Map;
import java.math.BigDecimal;

import com.upe.loja.business.FuncionarioBusiness;
import com.upe.loja.business.interfaces.IFuncionarioBusiness;
import com.upe.loja.repository.entity.Funcionario;
import com.upe.loja.repository.entity.Funcionario.Cargo;

import com.upe.loja.business.ClienteBusiness;
import com.upe.loja.business.interfaces.IClienteBusiness;
import com.upe.loja.repository.entity.Cliente;

public class Facade {
    private final IClienteBusiness clienteBusiness;
    private final IFuncionarioBusiness funcionarioBusiness;

    public Facade() {
        this.clienteBusiness = new ClienteBusiness();
        this.funcionarioBusiness = new FuncionarioBusiness();
    }

    public void cadastrarCliente(String cpf, String senha, String nome, String email) {
        clienteBusiness.cadastrarCliente(cpf, senha, nome, email);
    }

    public void atualizarCliente(String cpf, int option, String valor) {
        clienteBusiness.atualizar(cpf, option, valor);
    }

    public Map<String, Cliente> listarTodosCliente() {
        return clienteBusiness.listarTodos();
    }

    public Cliente buscarPorCpf(String cpf) {
        return clienteBusiness.buscarPorCpf(cpf);
    } //so ta aq porque tem uma função para pesquisar cliente

    public void removerCliente(String cpf) {
        clienteBusiness.remover(cpf);
    }

    public boolean podeAlugar(String cpf) {
        return clienteBusiness.podeAlugar(cpf);
    }

    public void cadastrarFuncionario(String cpf, String senha, String nome, String email, BigDecimal salario, Cargo cargo){
        funcionarioBusiness.cadastrarFuncionario(cpf, senha, nome, email, salario, cargo);
    }

    public Map<String, Funcionario> listarTodosFuncionario(){
        return funcionarioBusiness.listarTodos();
    }

    public void atualizarFuncionario(String cpf, int option, String valor){
        funcionarioBusiness.atualizar(cpf, option, valor);
    }

    public void removerFuncionario(String cpf){
        funcionarioBusiness.remover(cpf);
    }

    public void fecharPrograma() {
        clienteBusiness.guardarDados();
        funcionarioBusiness.guardarDados();
    }
    
}
