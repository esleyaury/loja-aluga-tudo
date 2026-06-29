package com.upe.loja.UI;

import com.upe.loja.Facade;
import com.upe.loja.repository.entity.Fornecedor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MenuFornecedor {
    private Scanner entrada;
    private int opc;
    private Facade facade;

    public MenuFornecedor(Facade facade){
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
                System.out.println("Erro: "+ e.getMessage());
            }
        } while(opc != 0);
    }

    public void exibirOpcoes(){
        System.out.println("------Menu fornecedor------");
        System.out.println("Escolha uma opção: ");
        System.out.println("1 - Cadastrar novo fornecedor");
        System.out.println("2 - Listar fornecedores");
        System.out.println("3 - Atualizar dados de um fornecedor");
        System.out.println("4 - Remover um fornecedor");
        System.out.println("0 - Fechar");
    }

    public void processarOpcao(int opc){
        switch(opc){
            case 1:
                menuCadastrarFornecedor();
                break;
            case 2:
                menuListarFornecedores();
                break;
            case 3:
                menuAtualizarFornecedor();
                break;
            case 4:
                menuRemoverFornecedor();
                break;
            case 0:
                facade.fecharPrograma();
                entrada.close();
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    public void menuCadastrarFornecedor(){
        boolean sucesso = false;
        while(!sucesso){
            System.out.println("Digite: CNPJ, Razão Social, Produto(s):\n");
            System.out.println("NÃO USE VÍRGULAS PARA SEPARAR OS PRODUTOS,APENAS ESPAÇO");
            String linha = entrada.nextLine();
            try{
                String[] partes = linha.split(",");
                String cnpj = partes[0];
                String razaoSocial = partes[1];
                Set <String> produtos = new HashSet<>(Arrays.asList(partes[2].trim().split(" ")));
                facade.cadastrarFornecedor(cnpj, razaoSocial, produtos);
                sucesso = true;
            } catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    public void menuListarFornecedores(){
        Map<String, Fornecedor> fornecedores = facade.listarTodos();
        for(Fornecedor f : fornecedores.values()){
            System.out.println("------------");
            System.out.println("CPF: " + f.getCnpj());
            System.out.println("Nome: " + f.getRazaoSocial());
            System.out.println("Senha: " + f.getProdutos());
        }
        System.out.println("------Fim------");
    }

    public void menuAtualizarFornecedor(){
        System.out.println("Digite o CNPJ do fornecedor que deseja alterar:\n");
        String cnpj = entrada.nextLine();
        boolean sucesso = false;
        do{
            try{
                System.out.println("O que deseja alterar?\n 1- Razão Social 2- Adicionar Produto 3- Remover Produto\n");
                int option = entrada.nextInt();
                entrada.nextLine();
                System.out.println("Qual valor inserir/retirar no lugar?\n");
                String valor = entrada.nextLine();
                facade.atualizar(cnpj, option, valor);
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

    public void menuRemoverFornecedor(){
        boolean sucesso = false;
        while(!sucesso){
            System.out.println("Digite o CNPJ do fornecedor que deseja remover:\n");
            String cnpj = entrada.nextLine();
            try{
                facade.remover(cnpj);
                System.out.println("Funcionário removido");
                sucesso = true;
            } catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }
}
