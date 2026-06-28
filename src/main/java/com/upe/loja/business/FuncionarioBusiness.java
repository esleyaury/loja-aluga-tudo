package com.upe.loja.business;

import java.util.Map;
import java.math.BigDecimal;

import com.upe.loja.business.interfaces.IFuncionarioBusiness;
import com.upe.loja.repository.entity.Funcionario;
import com.upe.loja.repository.entity.Funcionario.Cargo;

public class FuncionarioBusiness implements IFuncionarioBusiness{
    private IFuncionarioBusiness funcionarios;
    
    public FuncionarioBusiness(){
        this.funcionarios = new FuncionarioBusiness();
    }
    
    @Override
    public void cadastrarFuncionario(String id, String cpf, String senha, String nome, String email, BigDecimal salario, Cargo cargo){
        Funcionario funcionario = new Funcionario(id, cpf, senha, nome, email, salario, cargo);
        salvar(funcionario);
    }

    @Override
    public void salvar(Funcionario funcionario){
        if (funcionario == null){
            throw new IllegalArgumentException("Funcionario não pode estar vázio");
        }

        if (funcionarios.buscarPorCpf(funcionario.getCpf()) != null){
            throw new IllegalArgumentException("O cpf já está cadastrado");
        }

        funcionarios.salvar(funcionario);
    }

    @Override
    public Funcionario buscarPorId(String id){
        return funcionarios.buscarPorId(id);
    }
    
    @Override
    public Funcionario buscarPorCpf(String cpf){
        return funcionarios.buscarPorCpf(cpf);
    }

    @Override
    public Map<String, Funcionario> listarTodos(){
        return funcionarios.listarTodos();
    }

    @Override
    public void atualizar(Funcionario funcionario, int option, String valor){
        if(funcionario == null){
            throw new IllegalArgumentException("Funcionário inválido para atualização");
        }

        if(valor == null || valor.trim().isEmpty()){
            throw new IllegalArgumentException("O novo valor não pode ser vazio");
        }

        switch (option){
            case 1 -> funcionario.setNome(valor.trim());
            case 2 -> funcionario.setSenha(valor.trim()); 
            case 3 -> funcionario.setEmail(valor);
            case 4 -> {
                try{
                    BigDecimal salario = new BigDecimal(valor.replace(",", "."));
                    if (salario.compareTo(BigDecimal.ZERO) < 0){
                        throw new IllegalArgumentException("O salário não pode ser negativo");
                    } 
                    funcionario.setSalario(salario);
                } catch(NumberFormatException e){
                    throw new IllegalArgumentException("Formato numérico inválido");
                }
            }
            case 5 -> {
                try{
                    Cargo novoCargo = Cargo.valueOf(valor.toUpperCase());
                    funcionario.setCargo(novoCargo);
                } catch(IllegalArgumentException e){
                    throw new IllegalArgumentException("Cargo inválido");
                }
            }
            default -> throw new IllegalArgumentException("Opção inválida");
        }
        funcionarios.atualizar(funcionario, option, valor);
    }

    @Override
    public void remover(String id){
        funcionarios.remover(id);
    }

    @Override
    public void guardarDados(){
        funcionarios.guardarDados();
    }
}
