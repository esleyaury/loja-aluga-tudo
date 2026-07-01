package com.upe.loja.repository.entity;

import java.math.BigDecimal;

public class Funcionario extends Usuario{

    private Cargo cargo;
    private BigDecimal salario;
    private boolean permissaoAdmin;

    public Funcionario(String cpf, String senha, String nome, String email, BigDecimal salario, Cargo cargo){
        super(cpf,senha,nome,email,TipoPerfil.FUNCIONARIO);
        this.salario = salario;
        this.cargo = cargo;
        this.permissaoAdmin = false;
    }

    protected Funcionario(String id, String cpf, String senha, String nome, 
        String email, boolean permissaoAdmin){
        
        super(cpf, senha, nome, email, TipoPerfil.ADMINISTRADOR);
        this.permissaoAdmin = permissaoAdmin;
    }

    public enum Cargo{
        ESTAGIARIO,
        ATENDENTE,
        CAIXA,
        GERENTE,
        CEO
    }
    public BigDecimal getSalario(){
        return this.salario;
    }
    public Cargo getCargo(){
        return this.cargo;
    }
    public void setSalario(BigDecimal salarioNovo){
        if (salarioNovo == null){
            throw new IllegalArgumentException("O Salário é um campo obrigatório.");
        }

        // verificação salarial
       // Estagiario: 200 -- 800
        if (this.cargo == Cargo.ESTAGIARIO && salarioNovo.compareTo(new BigDecimal("200")) < 0 || salarioNovo.compareTo(new BigDecimal("800")) > 0){
            throw new IllegalArgumentException("Salário de estagiário fora de escopo");
        }
       //Atendente: 1500 -- 4000
        if (this.cargo == Cargo.ATENDENTE && salarioNovo.compareTo(new BigDecimal("1500")) < 0 || salarioNovo.compareTo(new BigDecimal("4000")) >0){
            throw new IllegalArgumentException("Salário de atendente fora de escopo");
        }
        //Caixa: 1500 -- 4000
        if (this.cargo == Cargo.CAIXA && salarioNovo.compareTo(new BigDecimal("1500")) < 0 || salarioNovo.compareTo(new BigDecimal("4000")) > 0){
            throw new IllegalArgumentException("Salário de caixa fora de escopo");
        }
        //Gerente: 5000 -- 15000
        if (this.cargo == Cargo.GERENTE && salarioNovo.compareTo(new BigDecimal("5000")) < 0 || salarioNovo.compareTo(new BigDecimal("15000")) > 0){
            throw new IllegalArgumentException("Salário de gerente fora de escopo");
        }
        //Ceo: 15000 -- o céu é o limite
        if (this.cargo == Cargo.CEO && salarioNovo.compareTo(new BigDecimal("15000")) < 0){
            throw new IllegalArgumentException("Salário de gerente fora de escopo");
        }

       this.salario = salarioNovo;
    }
    public void setCargo(Cargo cargo){
        this.cargo = cargo;
    }

    public boolean getPermissao(){
        return this.permissaoAdmin;
    }

    public void setSenha(String senha){
        if(senha == null){
            throw new IllegalArgumentException("Senha não pode ser nula");
        }
        super.setSenha(senha);
    }
}
