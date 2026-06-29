package com.upe.loja.UI;

import java.util.Scanner;

public class Menu {
   private Scanner entrada;

   public Menu(Scanner entrada) {
      this.entrada = entrada;
   }

   public void iniciar() {
      int opc = -1;

      do {
         this.exibirOpcoes();
         String digitado = this.entrada.nextLine();

         try {
            opc = Integer.parseInt(digitado);
            this.processarOpcao(opc);
         } catch (NumberFormatException var4) {
            System.out.println("Entrada Inválida! Por favor, digite um número.");
         }
      } while(opc != 0);

   }

   private void exibirOpcoes() {
      System.out.println("Menu da Loja MENOB's");
      System.out.println("4 - Gerenciar Fornecedores (Apenas ADM)"); 
      System.out.print("Escolha uma opção: ");
   }

   public void processarOpcao(int opc) {
      if (opc == 4) {
         FornecedorMenu menuFornecedor = new FornecedorMenu(this.entrada);
         menuFornecedor.iniciar();
      }
   }
}