package com.upe.loja.UI;

import com.upe.loja.Facade;
import com.upe.loja.repository.entity.Administrador;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Map;

public class MenuAdministrador {
    private Scanner entrada;
    private int opc;
    private Facade facade;

    public MenuAdministrador(Facade facade){
        this.entrada = new Scanner(System.in);
        this.opc = -1;
        this.facade = facade;
    }

    public void iniciar(){
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
        System.out.println("------Menu Administrador------");
        System.out.println("Escolha uma opção: ");
        System.out.println("1 - Cadastrar Administrador");
        System.out.println("2 - Listar Administradores");
        System.out.println("3 - Atualizar Administrador");
        System.out.println("4 - Remover Administrador");
        System.out.println("0 - Voltar");
    }

    public void processarOpcao(int opc){
        switch(opc){
            case 1:
                menuCadastrarAdministrador();
                break;
            case 2:
                menuListarAdministradores();
                break;
            case 3:
                menuAtualizarAdministrador();
                break;
            case 4:
                menuRemoverAdministrador();
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    public void menuCadastrarAdministrador(){
        boolean sucesso = false;
        while(!sucesso){
            System.out.println("Digite: cpf, senha, nome, email: ");
            String linha = entrada.nextLine();
            try{
                String[] partes = linha.split(",");
                if(partes.length < 4){
                    throw new IllegalArgumentException("Formato inválido");
                }
                String cpf = partes[0].trim();
                String senha = partes[1].trim();
                String nome = partes[2].trim();
                String email = partes[3].trim();
                facade.cadastrarAdministrador(cpf, senha, nome, email);
                sucesso = true;
            } catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    public void menuListarAdministradores(){
        Map<String, Administrador> administradores = facade.listarTodosAdministrador();
        for(Administrador a : administradores.values()){
            System.out.println("------------");
            System.out.println("CPF: " + a.getCpf());
            System.out.println("Nome: " + a.getNome());
            System.out.println("E-mail: " + a.getEmail());
            System.out.println("Ativo: " + a.isAtivo());
        }
        System.out.println("------Fim------");
    }

    public void menuAtualizarAdministrador(){
        System.out.println("Digite o CPF do administrador que deseja alterar:\n");
        String cpf = entrada.nextLine();
        boolean sucesso = false;
        do{
            try{
                System.out.println("O que deseja alterar?\n 1- CPF 2- EMAIL 3- NOME 4- SENHA 5- ATIVO\n");
                int option = entrada.nextInt();
                entrada.nextLine();
                System.out.println("Qual valor inserir no lugar?\n");
                String valor = entrada.nextLine();
                facade.atualizarAdministrador(cpf, option, valor);
                System.out.println("Administrador atualizado");
                sucesso = true;
            } catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            } catch(InputMismatchException e){
                System.err.println("Entrada inválida");
                entrada.nextLine();
            }
        } while(!sucesso);
    }

    public void menuRemoverAdministrador(){
        boolean sucesso = false;
        while(!sucesso){
            System.out.println("Digite o CPF do administrador que deseja remover:\n");
            String cpf = entrada.nextLine();
            try{
                facade.removerAdministrador(cpf);
                System.out.println("Administrador removido");
                sucesso = true;
            } catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }
}
