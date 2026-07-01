package com.upe.loja.UI;

import com.upe.loja.Facade;
import com.upe.loja.repository.entity.Cliente;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private Scanner entrada;
    private int opc;
    private Facade facade;

    public Menu(Facade facade) {
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
        System.out.println("------ Menu de Clientes - Loja Aluga Tudo -----");
        System.out.println("Escolha uma opção: ");
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Listar Clientes");
        System.out.println("3 - Buscar Cliente por Nome");
        System.out.println("4 - Buscar Cliente por CPF");
        System.out.println("5 - Atualizar Cliente");
        System.out.println("6 - Remover Cliente");
        System.out.println("7 - Verificar Situação de Aluguel (Inadimplência)");
        System.out.println("0 - Sair");
        System.out.println("Digite sua escolha abaixo: ");
    }

    public void processarOpcao(int opc) {
        switch (opc) {
            case 1:
                menuCadastrarCliente();
                break;
            case 2:
                menuListarClientes();
                break;
            case 3:
                menuBuscarPorNome();
                break;
            case 4:
                menuBuscarPorCpf();
                break;
            case 5:
                menuAtualizarCliente();
                break;
            case 6:
                menuRemoverCliente();
                break;
            case 7:
                menuVerificarSituacao();
                break;
            case 0:
                facade.fecharPrograma();
                entrada.close();
                break;
            default:
                System.out.println("Opção Invalida!!!");
        }
    }

    public void menuCadastrarCliente() {
        boolean sucesso = false;
        while (!sucesso) {
            System.out.println("Digite: cpf, senha, nome, email");
            String linha = entrada.nextLine();
            try {
                String[] partes = linha.split(",");
                if (partes.length < 4) {
                    throw new IllegalArgumentException("Formato inválido. Use vírgulas para separar os 4 campos.");
                }
                String cpf = partes[0].trim();
                String senha = partes[1].trim();
                String nome = partes[2].trim();
                String email = partes[3].trim();
                facade.cadastrarCliente(cpf, senha, nome, email);
                System.out.println("Cliente cadastrado.");
                sucesso = true;
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    public void menuListarClientes() {
        Map<String, Cliente> clientes = facade.listarTodosCliente();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente c : clientes.values()) {
            imprimirCliente(c);
        }
        System.out.println("-----------------------------------");
    }

    public void menuBuscarPorNome() {
        System.out.println("Digite o nome do cliente que deseja buscar:\n");
        String nome = entrada.nextLine();
        List<Cliente> encontrados = facade.buscarPorNome(nome);
        if (encontrados.isEmpty()) {
            System.out.println("Nenhum cliente encontrado com esse nome.");
            return;
        }
        for (Cliente c : encontrados) {
            imprimirCliente(c);
        }
        System.out.println("-----------------------------------");
    }

    public void menuBuscarPorCpf() {
        System.out.println("Digite o CPF do cliente que deseja buscar:\n");
        String cpf = entrada.nextLine().trim();
        Cliente cliente = facade.buscarPorCpf(cpf);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        imprimirCliente(cliente);
        System.out.println("-----------------------------------");
    }

    public void menuAtualizarCliente() {
        System.out.println("Digite o CPF do cliente que deseja atualizar:\n");
        String cpf = entrada.nextLine().trim();
        Cliente clienteEncontrado = facade.buscarPorCpf(cpf);
        if (clienteEncontrado == null) {
            System.out.println("Cliente não localizado.");
            return;
        }

        boolean sucesso = false;

        do {
            try {
                System.out.println("O que deseja alterar? \n 1-NOME 2-EMAIL 3-SENHA 4-ATIVO (true/false)\n");
                int option = Integer.parseInt(entrada.nextLine().trim());
                System.out.println("O que deseja inserir no lugar?\n");
                String valor = entrada.nextLine();
                facade.atualizarCliente(clienteEncontrado, option, valor);
                System.out.println("Cliente atualizado.");
                sucesso = true;
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida! Digite um número.\n");
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: " + e.getMessage() + "\n Tente novamente\n");
            }
        } while (!sucesso);

    }

    public void menuRemoverCliente() {
        System.out.println("Digite o CPF do cliente que deseja remover:\n");
        String cpf = entrada.nextLine().trim();
        try {
            Cliente clienteEncontrado = facade.buscarPorCpf(cpf);
            if (clienteEncontrado == null) {
                throw new IllegalArgumentException("Cliente não encontrado.");
            }
            facade.removerCliente(clienteEncontrado.getCpf());
            System.out.println("Cliente removido (exclusão lógica: ativo = false).");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void menuVerificarSituacao() {
        System.out.println("Digite o CPF do cliente:\n");
        String cpf = entrada.nextLine().trim();
        try {
            boolean podeAlugar = facade.podeAlugar(cpf);
            if (podeAlugar) {
                System.out.println("Cliente apto a realizar novos aluguéis.");
            } else {
                System.out.println("Cliente NÃO apto a realizar novos aluguéis (inativo e/ou com multas em aberto).");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private void imprimirCliente(Cliente c) {
        System.out.println("-----------------------------------");
        System.out.println("CPF: " + c.getCpf());
        System.out.println("Nome: " + c.getNome());
        System.out.println("Email: " + c.getEmail());
        System.out.println("Ativo: " + (c.isAtivo() ? "Sim" : "Não"));
        System.out.println("Inadimplente: " + (c.isInadimplente() ? "Sim" : "Não"));
    }
}
