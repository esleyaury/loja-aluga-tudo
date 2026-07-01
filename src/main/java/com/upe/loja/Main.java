package com.upe.loja;

import java.util.Scanner;

import com.upe.loja.UI.Menu;
import com.upe.loja.UI.MenuCategoria;
import com.upe.loja.UI.MenuFuncionario;
import com.upe.loja.UI.MenuProduto;
import com.upe.loja.UI.FornecedorMenu;
import com.upe.loja.UI.MenuAdministrador;

public class Main {

    public static void main(String[] args) {
        Facade facade = new Facade();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("====== Loja Aluga Tudo ======");
            System.out.println("Escolha um módulo:");
            System.out.println("1 - Clientes");
            System.out.println("2 - Produtos");
            System.out.println("3 - Categorias");
            System.out.println("4 - Funcionários");
            System.out.println("5 - Fornecedores");
            System.out.println("6 - Administradores");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        new Menu(facade).iniciar();
                        break;
                    case 2:
                        new MenuProduto(facade).iniciar();
                        break;
                    case 3:
                        new MenuCategoria(facade).iniciar();
                        break;
                    case 4:
                        new MenuFuncionario(facade).iniciar();
                        break;
                    case 5:
                        new FornecedorMenu(facade).iniciar();
                        break;
                    case 6:
                        new MenuAdministrador(facade).iniciar();
                        break;
                    case 0:
                        facade.fecharPrograma();
                        System.out.println("Obrigado por usar a Loja Aluga Tudo. Até logo!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }
}
