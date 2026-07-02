package com.upe.loja;

import java.util.Scanner;

import com.upe.loja.UI.Menu;
import com.upe.loja.UI.MenuAreaCliente;
import com.upe.loja.UI.MenuCategoria;
import com.upe.loja.UI.MenuContrato;
import com.upe.loja.UI.MenuFuncionario;
import com.upe.loja.UI.MenuOcorrencia;
import com.upe.loja.UI.MenuProduto;
import com.upe.loja.UI.MenuRelatorios;
import com.upe.loja.UI.FornecedorMenu;
import com.upe.loja.UI.MenuAdministrador;

public class Main {

    public static void main(String[] args) {
        Facade facade = new Facade();
        Scanner scanner = new Scanner(System.in);

        garantirAdministradorInicial(facade, scanner);

        while (true) {
            Sessao sessao = login(facade, scanner);
            if (sessao == null) {
                facade.fecharPrograma();
                System.out.println("Obrigado por usar a Loja Aluga Tudo. Até logo!");
                scanner.close();
                return;
            }

            switch (sessao.tipo()) {
                case ADMINISTRADOR -> menuAdministrador(facade, scanner, sessao);
                case FUNCIONARIO   -> menuFuncionario(facade, scanner, sessao);
                case CLIENTE       -> new MenuAreaCliente(facade, sessao.cpf()).iniciar();
            }
        }
    }

    private static void garantirAdministradorInicial(Facade facade, Scanner scanner) {
        if (!facade.listarTodosAdministrador().isEmpty()) {
            return;
        }

        System.out.println("====== Loja Aluga Tudo - Configuração Inicial ======");
        System.out.println("Nenhum administrador cadastrado. Vamos criar o primeiro.");

        while (true) {
            try {
                System.out.print("CPF: ");
                String cpf = scanner.nextLine().trim();
                System.out.print("Senha: ");
                String senha = scanner.nextLine();
                System.out.print("Nome: ");
                String nome = scanner.nextLine().trim();
                System.out.print("Email: ");
                String email = scanner.nextLine().trim();

                facade.cadastrarAdministrador(cpf, senha, nome, email);
                System.out.println("Administrador criado com sucesso. Faça login para continuar.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static Sessao login(Facade facade, Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("====== Loja Aluga Tudo - Login ======");
            System.out.print("CPF (ou 0 para sair): ");
            String cpf = scanner.nextLine().trim();

            if (cpf.equals("0")) {
                return null;
            }

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            Sessao sessao = facade.autenticar(cpf, senha);
            if (sessao == null) {
                System.out.println("CPF ou senha inválidos.");
                continue;
            }

            System.out.println("Bem-vindo(a), " + sessao.nome() + "!");
            return sessao;
        }
    }

    private static void menuAdministrador(Facade facade, Scanner scanner, Sessao sessao) {
        int opcao;
        do {
            System.out.println();
            System.out.println("====== Loja Aluga Tudo - Administrador ======");
            System.out.println("1 - Clientes");
            System.out.println("2 - Produtos");
            System.out.println("3 - Categorias");
            System.out.println("4 - Funcionários");
            System.out.println("5 - Fornecedores");
            System.out.println("6 - Administradores");
            System.out.println("7 - Contratos");
            System.out.println("8 - Ocorrências/Multas");
            System.out.println("9 - Relatórios");
            System.out.println("0 - Logout");
            System.out.print("Opção: ");

            opcao = lerOpcao(scanner);
            switch (opcao) {
                case 1 -> new Menu(facade).iniciar();
                case 2 -> new MenuProduto(facade).iniciar();
                case 3 -> new MenuCategoria(facade).iniciar();
                case 4 -> new MenuFuncionario(facade).iniciar();
                case 5 -> new FornecedorMenu(facade).iniciar();
                case 6 -> new MenuAdministrador(facade).iniciar();
                case 7 -> new MenuContrato(facade).iniciar();
                case 8 -> new MenuOcorrencia(facade).iniciar();
                case 9 -> new MenuRelatorios(facade, true).iniciar();
                case 0 -> System.out.println("Logout efetuado.");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static void menuFuncionario(Facade facade, Scanner scanner, Sessao sessao) {
        int opcao;
        do {
            System.out.println();
            System.out.println("====== Loja Aluga Tudo - Funcionário ======");
            System.out.println("1 - Clientes");
            System.out.println("2 - Produtos");
            System.out.println("3 - Contratos");
            System.out.println("4 - Ocorrências/Multas");
            System.out.println("5 - Relatórios");
            System.out.println("0 - Logout");
            System.out.print("Opção: ");

            opcao = lerOpcao(scanner);
            switch (opcao) {
                case 1 -> new Menu(facade).iniciar();
                case 2 -> new MenuProduto(facade).iniciar();
                case 3 -> new MenuContrato(facade).iniciar();
                case 4 -> new MenuOcorrencia(facade).iniciar();
                case 5 -> new MenuRelatorios(facade, false).iniciar();
                case 0 -> System.out.println("Logout efetuado.");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private static int lerOpcao(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Digite um número.");
            return -1;
        }
    }
}
