package com.upe.loja.UI;

import java.util.Scanner;

public class Menu{
    private Scanner entrada;
    private int opc;

    public Menu(Scanner entrada){
        this.entrada = new Scanner(System.in);
        this.opc = -1;
    }

    public void iniciar(){
        
        do{
            exibirOpcoes();
            String digitado = entrada.nextLine();
            try{
                opc = Integer.parseInt(digitado);
                processarOpcao(opc);
            } catch(NumberFormatException e){
                System.out.println("Entrada Inválida! Por favor, digite um número.");
            }
        } while (opc != 0) ;
    }
    private void exibirOpcoes(){
        System.out.println("------ Menu da Loja MENOB's -----");
        System.out.println("Escolha uma opção: ");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar Produtos");
        System.out.println("3 - Atualizar Produto");
        System.out.println("4 - Remover");
        System.out.println("0 - Sair");
        System.out.println("Digite sua escolha abaixo: ");
    }
    public void processarOpcao(int opc){
        switch(opc){
            case 1:
                // Cadastrar novo produto
                // Instancia um novo produto, e salva ele
                break;
            case 2:
                // ListarTodos();
                break;
            case 3:
                //Atualizar o produto, e salvar ele
                break;
            case 4:
                // Remove o produto, e precisa salvar isso.
            case 0:
                break;
            default:
                System.out.println("Opção Invalida!!!");
        }
    }
}