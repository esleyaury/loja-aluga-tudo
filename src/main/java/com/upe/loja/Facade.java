package com.upe.loja;

import java.util.List;
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

    public Cliente buscarPorId(String id) {
        return clienteBusiness.buscarPorId(id); //vai sair
    }

    public Cliente buscarPorCpf(String cpf) {
        return clienteBusiness.buscarPorCpf(cpf); //fazer as verificações q precisam disso no business e tirar isso daq do facade
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteBusiness.buscarPorNome(nome);
    }

    public void atualizarCliente(Cliente cliente, int option, String valor) {
        clienteBusiness.atualizar(cliente, option, valor);
    }

    public Map<String, Cliente> listarTodosCliente() {
        return clienteBusiness.listarTodos();
    }

    public void removerCliente(String id) {
        clienteBusiness.remover(id);
    }

    public boolean podeAlugar(String cpf) {
        return clienteBusiness.podeAlugar(cpf);
    }

    public void cadastrarFuncionario(String cpf, String senha, String nome, String email, BigDecimal salario, Cargo cargo){
        funcionarioBusiness.cadastrarFuncionario(cpf, senha, nome, email, salario, cargo);
    }

    public Funcionario buscarPorCpfFuncionario(String cpf){ //fazer as verificações q precisam disso no business e tirar isso daq do facade
        return funcionarioBusiness.buscarPorCpf(cpf);
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
