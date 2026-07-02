package com.upe.loja.UI;

import com.upe.loja.Facade;
import com.upe.loja.business.RelatorioBusiness.ItemAlugado;
import com.upe.loja.business.RelatorioBusiness.RelatorioFaturamento;
import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Produto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuRelatorios {

    private final Scanner entrada;
    private final Facade facade;
    private final boolean acessoFinanceiro;
    private int opc;

    public MenuRelatorios(Facade facade, boolean acessoFinanceiro) {
        this.entrada = new Scanner(System.in);
        this.facade  = facade;
        this.acessoFinanceiro = acessoFinanceiro;
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
        System.out.println("------ Relatórios ------");
        System.out.println("1 - Itens disponíveis por categoria");
        System.out.println("2 - Histórico de aluguéis de um cliente");
        System.out.println("3 - Itens alugados no momento (com atraso)");
        if (acessoFinanceiro) {
            System.out.println("4 - Faturamento em um período");
        }
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");
    }

    private void processarOpcao(int opc) {
        switch (opc) {
            case 1 -> menuItensPorCategoria();
            case 2 -> menuHistoricoCliente();
            case 3 -> menuItensAlugados();
            case 4 -> {
                if (acessoFinanceiro) {
                    menuFaturamento();
                } else {
                    System.out.println("Opção inválida.");
                }
            }
            case 0 -> System.out.println("Voltando...");
            default -> System.out.println("Opção inválida.");
        }
    }

    private void menuItensPorCategoria() {
        Map<String, List<Produto>> porCategoria = facade.relatorioItensDisponiveisPorCategoria();

        if (porCategoria.isEmpty()) {
            System.out.println("Nenhum item disponível no momento.");
            return;
        }

        System.out.println("\n--- Itens Disponíveis por Categoria ---");
        for (Map.Entry<String, List<Produto>> entrada : porCategoria.entrySet()) {
            System.out.println(entrada.getKey() + ":");
            for (Produto p : entrada.getValue()) {
                System.out.printf("  ID: %s | Nome: %s | Taxa Diária: R$ %s%n",
                        p.getID(), p.getNome(), p.getTaxaDiaria());
            }
        }
    }

    private void menuHistoricoCliente() {
        System.out.println("CPF do cliente:");
        String cpf = entrada.nextLine().trim();

        List<Contrato> historico = facade.relatorioHistoricoAlugueis(cpf);

        if (historico.isEmpty()) {
            System.out.println("Nenhum aluguel encontrado para este cliente.");
            return;
        }

        System.out.println("\n--- Histórico de Aluguéis: " + cpf + " ---");
        for (Contrato c : historico) {
            System.out.printf("ID: %d | Produtos: %s | Retirada: %s | Devolução Prevista: %s | Valor: R$ %s | Status: %s%n",
                    c.getId(), String.join(",", c.getIdsProdutos()),
                    c.getDataRetirada(), c.getDataDevolucaoPrevista(),
                    c.getValorTotal(), c.getStatus());
        }
    }

    private void menuItensAlugados() {
        List<ItemAlugado> itens = facade.relatorioItensAlugadosNoMomento();

        if (itens.isEmpty()) {
            System.out.println("Nenhum item alugado no momento.");
            return;
        }

        System.out.println("\n--- Itens Alugados no Momento ---");
        for (ItemAlugado item : itens) {
            Contrato c = item.contrato();
            System.out.printf("ID: %d | Cliente: %s | Produtos: %s | Devolução Prevista: %s | %s%n",
                    c.getId(), c.getCpfCliente(), String.join(",", c.getIdsProdutos()),
                    c.getDataDevolucaoPrevista(), item.emAtraso() ? "⚠ EM ATRASO" : "No prazo");
        }
    }

    private void menuFaturamento() {
        try {
            System.out.println("Data inicial (AAAA-MM-DD):");
            LocalDate inicio = LocalDate.parse(entrada.nextLine().trim());

            System.out.println("Data final (AAAA-MM-DD):");
            LocalDate fim = LocalDate.parse(entrada.nextLine().trim());

            RelatorioFaturamento relatorio = facade.relatorioFaturamento(inicio, fim);

            System.out.println("\n--- Faturamento de " + inicio + " a " + fim + " ---");
            System.out.printf("Total em aluguéis: R$ %s%n", relatorio.totalAlugueis());
            System.out.printf("Total em multas:   R$ %s%n", relatorio.totalMultas());
            System.out.printf("Total geral:       R$ %s%n", relatorio.totalGeral());

        } catch (DateTimeParseException e) {
            System.err.println("Formato de data inválido. Use AAAA-MM-DD.");
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
