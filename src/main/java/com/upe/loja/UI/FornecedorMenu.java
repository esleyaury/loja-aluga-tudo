package com.upe.loja.UI;

import com.upe.loja.Facade;
import com.upe.loja.repository.entity.Fornecedor;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class FornecedorMenu {
    private Scanner entrada;
    private int opc;
    private Facade facade;

    public FornecedorMenu(Facade facade) {
        this.entrada = new Scanner(System.in);
        this.opc = -1;
        this.facade = facade;
    }

    public void iniciar() {
        do {
            exibirOpcoes();
            String digitado = entrada.nextLine();
            try {
                opc = Integer.parseInt(digitado);
                processarOpcao(opc);
            } catch (NumberFormatException e) {
                System.out.println("Entrada Inválida! Por favor, digite um número.");
            } catch (Exception e) {
                System.out.println("Erro na operação: " + e.getMessage() + "\n");
            }
        } while (opc != 0);
    }

    private void exibirOpcoes() {
        System.out.println("------ Menu de Fornecedores -----");
        System.out.println("1 - Cadastrar Fornecedor");
        System.out.println("2 - Listar Fornecedores");
        System.out.println("3 - Atualizar Fornecedor");
        System.out.println("4 - Remover Fornecedor");
        System.out.println("0 - Voltar");
        System.out.println("Digite sua escolha abaixo: ");
    }

    public void processarOpcao(int opc) {
        switch (opc) {
            case 1:
                menuCadastrarFornecedor();
                break;
            case 2:
                menuListarTodos();
                break;
            case 3:
                menuAtualizarFornecedor();
                break;
            case 4:
                menuRemoverFornecedor();
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println("Opção Inválida!");
        }
    }

    public void menuCadastrarFornecedor() {
        boolean sucesso = false;
        while (!sucesso) {
            System.out.println("Digite: cnpj, nome, telefone (separados por vírgula)");
            String linha = entrada.nextLine();
            try {
                String[] partes = linha.split(",");
                if (partes.length < 3) {
                    throw new IllegalArgumentException("Formato inválido. Use vírgulas para separar os 3 campos.");
                }
                String cnpj = partes[0].trim();
                String nome = partes[1].trim();
                String telefone = partes[2].trim();
                Fornecedor novoFornecedor = new Fornecedor(cnpj, nome, telefone);
                facade.salvarFornecedor(novoFornecedor);
                System.out.println("Fornecedor cadastrado.");
                sucesso = true;
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    public void menuListarTodos() {
        List<Fornecedor> fornecedores = facade.listarFornecedores();
        if (fornecedores.isEmpty()) {
            System.out.println("Nenhum fornecedor cadastrado.");
            return;
        }
        for (Fornecedor f : fornecedores) {
            System.out.println("-----------------------------------");
            System.out.println("CNPJ: " + f.getCnpj());
            System.out.println("Nome: " + f.getNome());
            System.out.println("Telefone: " + f.getTelefone());
        }
        System.out.println("-----------------------------------");
    }

    public void menuAtualizarFornecedor() {
        System.out.println("Digite o CNPJ do fornecedor que deseja atualizar:");
        String cnpj = entrada.nextLine();
        boolean sucesso = false;
        do {
            try {
                System.out.println("O que deseja alterar?\n 1- NOME 2- CNPJ 3- TELEFONE");
                int option = entrada.nextInt();
                entrada.nextLine();
                System.out.println("Qual valor inserir no lugar?");
                String valor = entrada.nextLine();
                facade.atualizarFornecedor(cnpj, option, valor);
                System.out.println("Fornecedor atualizado.");
                sucesso = true;
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida! Digite um número.");
                entrada.nextLine();
            }
        } while (!sucesso);
    }

    public void menuRemoverFornecedor() {
        boolean sucesso = false;
        while (!sucesso) {
            System.out.println("Digite o CNPJ do fornecedor que deseja remover:");
            String cnpj = entrada.nextLine();
            try {
                facade.removerFornecedor(cnpj);
                System.out.println("Fornecedor removido.");
                sucesso = true;
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }
}