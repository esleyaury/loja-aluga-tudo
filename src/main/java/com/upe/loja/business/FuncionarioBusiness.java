package com.upe.loja.business;

import java.util.Map;
import java.math.BigDecimal;

import com.upe.loja.business.interfaces.IFuncionarioBusiness;
import com.upe.loja.repository.interfaces.IFuncionarioRepository;
import com.upe.loja.repository.FuncionarioRepository;
import com.upe.loja.repository.entity.Funcionario;
import com.upe.loja.repository.entity.Funcionario.Cargo;

public class FuncionarioBusiness implements IFuncionarioBusiness{
    private IFuncionarioRepository funcionarios;
    
    public FuncionarioBusiness(){
        this.funcionarios = new FuncionarioRepository();
    }
    
    @Override
    public void cadastrarFuncionario(String cpf, String senha, String nome, String email, BigDecimal salario, Cargo cargo){
        if (funcionarios.buscarPorCpf(cpf) != null){
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        Funcionario funcionario = new Funcionario(cpf, senha, nome, email, salario, cargo);
        funcionarios.salvar(funcionario);
    }

    @Override
    public void salvar(Funcionario funcionario){
        funcionarios.salvar(funcionario);
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
    public void atualizar(String cpf, int option, String valor){
        Funcionario funcionario = funcionarios.buscarPorCpf(cpf);
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
        funcionarios.atualizar(funcionario);
    }

    @Override
    public void remover(String cpf){
        Funcionario funcionario = funcionarios.buscarPorCpf(cpf);
        if(funcionario == null){
            throw new IllegalArgumentException("Funcionário inválido para atualização");
        }
        funcionarios.remover(cpf);
    }

    @Override
    public void guardarDados(){
        funcionarios.guardarDados();
    }
}
