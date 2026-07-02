package com.upe.loja.UI;

import com.upe.loja.Facade;
import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Contrato.StatusContrato;
import com.upe.loja.repository.entity.Ocorrencia;
import com.upe.loja.repository.entity.Produto;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuAreaCliente {

    private final Scanner entrada;
    private final Facade facade;
    private final String cpfCliente;
    private int opc;

    public MenuAreaCliente(Facade facade, String cpfCliente) {
        this.entrada    = new Scanner(System.in);
        this.facade     = facade;
        this.cpfCliente = cpfCliente;
        this.opc        = -1;
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
        System.out.println("------ Área do Cliente ------");
        System.out.println("1 - Ver itens disponíveis para aluguel");
        System.out.println("2 - Ver meus aluguéis ativos");
        System.out.println("3 - Ver meu histórico de aluguéis");
        System.out.println("4 - Ver minhas multas pendentes");
        System.out.println("0 - Logout");
        System.out.print("Escolha: ");
    }

    private void processarOpcao(int opc) {
        switch (opc) {
            case 1 -> menuItensDisponiveis();
            case 2 -> menuMeusAluguelAtivos();
            case 3 -> menuMeuHistorico();
            case 4 -> menuMinhasMultasPendentes();
            case 0 -> System.out.println("Saindo da área do cliente...");
            default -> System.out.println("Opção inválida.");
        }
    }

    private void menuItensDisponiveis() {
        Map<String, Produto> disponiveis = facade.produtosDisponiveis();
        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum item disponível no momento.");
            return;
        }
        System.out.println("\n--- Itens Disponíveis ---");
        for (Produto p : disponiveis.values()) {
            System.out.printf("ID: %s | Nome: %s | Categoria: %s | Taxa Diária: R$ %s%n",
                    p.getID(), p.getNome(), p.getCategoria(), p.getTaxaDiaria());
        }
    }

    private void menuMeusAluguelAtivos() {
        List<Contrato> ativos = facade.buscarContratosPorCliente(cpfCliente).stream()
                .filter(c -> c.getStatus() == StatusContrato.ATIVO)
                .toList();

        if (ativos.isEmpty()) {
            System.out.println("Você não possui aluguéis ativos no momento.");
            return;
        }
        System.out.println("\n--- Meus Aluguéis Ativos ---");
        for (Contrato c : ativos) {
            System.out.printf("ID: %d | Produtos: %s | Retirada: %s | Devolução Prevista: %s | Valor: R$ %s%n",
                    c.getId(), String.join(",", c.getIdsProdutos()),
                    c.getDataRetirada(), c.getDataDevolucaoPrevista(), c.getValorTotal());
        }
    }

    private void menuMeuHistorico() {
        List<Contrato> historico = facade.buscarContratosPorCliente(cpfCliente);

        if (historico.isEmpty()) {
            System.out.println("Você ainda não possui histórico de aluguéis.");
            return;
        }
        System.out.println("\n--- Meu Histórico de Aluguéis ---");
        for (Contrato c : historico) {
            System.out.printf("ID: %d | Produtos: %s | Retirada: %s | Devolução Prevista: %s | Valor: R$ %s | Status: %s%n",
                    c.getId(), String.join(",", c.getIdsProdutos()),
                    c.getDataRetirada(), c.getDataDevolucaoPrevista(),
                    c.getValorTotal(), c.getStatus());
        }
    }

    private void menuMinhasMultasPendentes() {
        List<Ocorrencia> pendentes = facade.minhasMultasPendentes(cpfCliente);

        if (pendentes.isEmpty()) {
            System.out.println("Você não possui multas pendentes.");
            return;
        }
        System.out.println("\n--- Minhas Multas Pendentes ---");
        for (Ocorrencia o : pendentes) {
            System.out.printf("Contrato: %d | Tipo: %s | Data: %s | Valor: R$ %s | Descrição: %s%n",
                    o.getIdContrato(), o.getTipo(), o.getDataOcorrencia(),
                    o.getValorMulta(), o.getDescricao());
        }
    }
}
