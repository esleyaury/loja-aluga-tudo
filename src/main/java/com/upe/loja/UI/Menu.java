package com.upe.loja.UI;

import java.util.Scanner;

public class Menu{
    private Scanner entrada;

    public Menu(Scanner entrada){
        this.entrada = entrada;
    }

    public void iniciar(){
        int opc = -1;
        
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
        System.out.println("Menu da Loja MENOB's");
        System.out.print("Escolha uma opção: ");
    }
    public void processarOpcao(int opc){
        }
}