package com.upe.loja.repository.entity;

import java.math.BigDecimal;

public class Funcionario extends Usuario{

    private Cargo cargo;
    private BigDecimal salario;

    public Funcionario(String id, String cpf, String senha, String nome, String email, BigDecimal salario, Cargo cargo){
        super(id,cpf,senha,nome,email,tipoPerfil.Funcionario);
        this.salario = salario;
        this.cargo = cargo;
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
        if (salarioNovo.compareTo(new BigDecimal("200"))<0){
            throw new IllegalArgumentException("Salário mínimo é R$ 200,00.");
        }
        /*verificação sobre o teto do salário
        Estagiario: 200 -- 800
        Atendente: 1500 -- 4000
        Caixa: 1500 -- 4000
        Gerente: 5000 -- 15000
        Ceo: 15000 -- o céu é o limite
        */
       this.salario = salarioNovo;
    }
    public void setCargo(Cargo cargo){
        this.cargo = cargo;
        /*Adicionar verificação para CEO, só pode ter 3 em ativação*/
    }
}
