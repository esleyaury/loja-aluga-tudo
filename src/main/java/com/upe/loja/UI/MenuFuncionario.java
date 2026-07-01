package com.upe.loja.UI;

import com.upe.loja.Facade;
import com.upe.loja.repository.entity.Funcionario.Cargo;
import com.upe.loja.repository.entity.*;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Map;

public class MenuFuncionario {
    //menu funcionario deveria ser com oq fṕode fazer em funcionario ou que funcionario pode fazer??
    private Scanner entrada;
    private int opc;
    private Facade facade;

    public MenuFuncionario(Facade facade){
        this.entrada = new Scanner(System.in);
        this.opc = -1;
        this.facade = facade;
    }

    public void iniciar(){ //sera q e melhor trocar os nomes p nao dar problemas na hora de chamar nos gerais?
        do{
            exibirOpcoes();
            String digitando = entrada.nextLine();
            try{
                opc = Integer.parseInt(digitando);
                processarOpcao(opc);
            } catch(NumberFormatException e){
                System.out.println("Entrada inválida");
            } catch(Exception e){
                System.out.println("Erro: " + e.getMessage());
            }
        } while(opc != 0);
    }

    public void exibirOpcoes(){
        System.out.println("------Menu funcionário------");
        System.out.println("Escolha uma opção: ");
        System.out.println("1 - Cadastrar novo funcionário");
        System.out.println("2 - Listar funcionários");
        System.out.println("3 - Atualizar dados de um funcionário");
        System.out.println("4 - Remover um funcionário");
        System.out.println("0 - Fechar");
    }

    public void processarOpcao(int opc){
        switch(opc){
            case 1:
                menuCadastrarFuncionario();
                break;
            case 2:
                menuListarFuncionarios();
                break;
            case 3:
                menuAtualizarFuncionario();
                break;
            case 4:
                menuRemoverFuncionario();
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    public void menuCadastrarFuncionario(){
        boolean sucesso = false;
        while(!sucesso){
            System.out.println("Digite: cpf, senha, nome, email, salário e cargo: ");
            String linha = entrada.nextLine();
            try{
                String[] partes = linha.split(",");
                if(partes.length < 6 ){
                    throw new IllegalArgumentException("Formato inválido");
                }
                String cpf = partes[0].trim();
                String senha = partes[1].trim();
                String nome = partes[2].trim();
                String email = partes[3].trim();
                BigDecimal salario = new BigDecimal(partes[4].trim());
                Cargo cargo = Cargo.valueOf(partes[5].trim().toUpperCase());
                facade.cadastrarFuncionario(cpf, senha, nome, email, salario, cargo);
                sucesso = true;
            } catch(NumberFormatException e){
                System.err.println("Salário está fora de formato");
            } catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    public void menuListarFuncionarios(){
        Map<String, Funcionario> funcionarios = facade.listarTodosFuncionario();
        for(Funcionario f : funcionarios.values()){
            System.out.println("------------");
            System.out.println("CPF: " + f.getCpf());
            System.out.println("Nome: " + f.getNome());
            System.out.println("Senha: " + f.getSenha());
            System.out.println("E-mail: " + f.getEmail());
            System.out.println("Salário: " + f.getSalario());
            System.out.println("Cargo: " + f.getCargo());
        }
        System.out.println("------Fim------");
    }

    public void menuAtualizarFuncionario(){
        System.out.println("Digite o CPF do funcionário que deseja alterar:\n");
        String cpf = entrada.nextLine();
        boolean sucesso = false;
        do{
            try{
                System.out.println("O que deseja alterar?\n 1- NOME 2- SENHA 3- EMAIL 4- SALÁRIO 5- CARGO\n");
                int option = entrada.nextInt();
                entrada.nextLine();
                System.out.println("Qual valor inserir no lugar?\n");
                String valor = entrada.nextLine();
                facade.atualizarFuncionario(cpf, option, valor);
                System.out.println("Funcionário atualizado");
                sucesso = true;
            } catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            } catch(InputMismatchException e){
                System.err.println("Entrada inválida");
                entrada.nextLine();
            }
        } while(!sucesso);
    }

    public void menuRemoverFuncionario(){
        boolean sucesso = false;
        while(!sucesso){
            System.out.println("Digite o CPF do funcionário que deseja remover:\n");
            String cpf = entrada.nextLine();
            try{
                facade.removerFuncionario(cpf);
                System.out.println("Funcionário removido");
                sucesso = true;
            } catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }
}
