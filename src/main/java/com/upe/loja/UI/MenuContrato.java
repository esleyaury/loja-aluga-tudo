package com.upe.loja.UI;

import com.upe.loja.Facade;
import com.upe.loja.repository.entity.Contrato;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuContrato {

    private final Scanner entrada;
    private final Facade facade;
    private int opc;

    public MenuContrato(Facade facade) {
        this.entrada = new Scanner(System.in);
        this.facade  = facade;
        this.opc     = -1;
    }

    public void iniciar() {
        do {
            exibirOpcoes();
            String digitado = entrada.nextLine();
            try {
                opc = Integer.parseInt(digitado);
                processarOpcao(opc);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opc != 0);
    }

    private void exibirOpcoes() {
        System.out.println("------ Menu de Contratos ------");
        System.out.println("1 - Criar contrato");
        System.out.println("2 - Listar contratos ativos");
        System.out.println("3 - Buscar contrato por ID");
        System.out.println("4 - Buscar contratos de um cliente");
        System.out.println("5 - Encerrar contrato");
        System.out.println("0 - Voltar");
        System.out.println("Escolha: ");
    }

    private void processarOpcao(int opc) {
        switch (opc) {
            case 1 -> menuCriarContrato();
            case 2 -> menuListarAtivos();
            case 3 -> menuBuscarPorId();
            case 4 -> menuBuscarPorCliente();
            case 5 -> menuEncerrarContrato();
            case 0 -> System.out.println("Voltando...");
            default -> System.out.println("Opção inválida.");
        }
    }

    private void menuCriarContrato() {
        boolean sucesso = false;
        while (!sucesso) {
            try {
                System.out.println("CPF do cliente:");
                String cpfCliente = entrada.nextLine().trim();

                System.out.println("CPF do funcionário:");
                String cpfFuncionario = entrada.nextLine().trim();

                System.out.println("IDs dos produtos (separados por vírgula):");
                String[] idsArray = entrada.nextLine().split(",");
                List<String> idsProdutos = Arrays.stream(idsArray)
                        .map(String::trim)
                        .toList();

                System.out.println("Data de retirada (AAAA-MM-DD):");
                LocalDate dataRetirada = LocalDate.parse(entrada.nextLine().trim());

                System.out.println("Data de devolução prevista (AAAA-MM-DD):");
                LocalDate dataDevolucao = LocalDate.parse(entrada.nextLine().trim());

                Contrato contrato = facade.criarContrato(cpfCliente, cpfFuncionario,
                        idsProdutos, dataRetirada, dataDevolucao);

                System.out.printf("Contrato criado! ID: %d | Valor Total: R$ %s%n",
                        contrato.getId(), contrato.getValorTotal());
                sucesso = true;

            } catch (DateTimeParseException e) {
                System.err.println("Formato de data inválido. Use AAAA-MM-DD.");
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    private void menuListarAtivos() {
        List<Contrato> ativos = facade.buscarContratosAtivos();
        LocalDate hoje = LocalDate.now();

        if (ativos.isEmpty()) {
            System.out.println("Nenhum contrato ativo no momento.");
            return;
        }

        System.out.println("\n--- Contratos Ativos ---");
        for (Contrato c : ativos) {
            boolean emAtraso = hoje.isAfter(c.getDataDevolucaoPrevista());
            System.out.printf("ID: %d | Cliente: %s | Produtos: %s | Devolução Prevista: %s | Valor: R$ %s | %s%n",
                    c.getId(), c.getCpfCliente(),
                    String.join(",", c.getIdsProdutos()),
                    c.getDataDevolucaoPrevista(),
                    c.getValorTotal(),
                    emAtraso ? "⚠ EM ATRASO" : "No prazo");
        }
    }

    private void menuBuscarPorId() {
        System.out.println("Digite o ID do contrato:");
        try {
            long id = Long.parseLong(entrada.nextLine().trim());
            Contrato contrato = facade.buscarContratoPorId(id);

            if (contrato == null) {
                System.out.println("Contrato não encontrado.");
                return;
            }

            String devolucaoReal = contrato.getDataDevolucaoReal() == null
                    ? "Em aberto" : contrato.getDataDevolucaoReal().toString();

            System.out.printf("ID: %d | Cliente: %s | Funcionário: %s | Produtos: %s%n" +
                            "Retirada: %s | Devolução Prevista: %s | Devolução Real: %s%n" +
                            "Valor: R$ %s | Status: %s%n",
                    contrato.getId(), contrato.getCpfCliente(), contrato.getCpfFuncionario(),
                    String.join(",", contrato.getIdsProdutos()),
                    contrato.getDataRetirada(), contrato.getDataDevolucaoPrevista(), devolucaoReal,
                    contrato.getValorTotal(), contrato.getStatus());

        } catch (NumberFormatException e) {
            System.err.println("ID inválido.");
        }
    }

    private void menuBuscarPorCliente() {
        System.out.println("Digite o CPF do cliente:");
        String cpf = entrada.nextLine().trim();

        List<Contrato> historico = facade.buscarContratosPorCliente(cpf);

        if (historico.isEmpty()) {
            System.out.println("Nenhum contrato encontrado para este cliente.");
            return;
        }

        System.out.println("\n--- Contratos do cliente " + cpf + " ---");
        for (Contrato c : historico) {
            System.out.printf("ID: %d | Produtos: %s | Retirada: %s | Devolução Prevista: %s | Valor: R$ %s | Status: %s%n",
                    c.getId(), String.join(",", c.getIdsProdutos()),
                    c.getDataRetirada(), c.getDataDevolucaoPrevista(),
                    c.getValorTotal(), c.getStatus());
        }
    }

    private void menuEncerrarContrato() {
        boolean sucesso = false;
        while (!sucesso) {
            System.out.println("Digite o ID do contrato que deseja encerrar:");
            try {
                long id = Long.parseLong(entrada.nextLine().trim());
                facade.encerrarContrato(id);
                System.out.println("Contrato encerrado com sucesso.");
                sucesso = true;
            } catch (NumberFormatException e) {
                System.err.println("ID inválido.");
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: " + e.getMessage());
                sucesso = true; // sai do loop para não ficar preso
            }
        }
    }
}