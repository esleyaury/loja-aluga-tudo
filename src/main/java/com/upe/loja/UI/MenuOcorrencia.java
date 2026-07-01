package com.upe.loja.UI;

import com.upe.loja.Facade;
import com.upe.loja.repository.entity.Ocorrencia;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;

public class MenuOcorrencia {

    private final Scanner entrada;
    private final Facade facade;
    private int opc;

    public MenuOcorrencia(Facade facade) {
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
        System.out.println("------ Menu de Ocorrências ------");
        System.out.println("1 - Registrar atraso");
        System.out.println("2 - Registrar avaria");
        System.out.println("3 - Quitar ocorrência");
        System.out.println("4 - Buscar ocorrência de um contrato");
        System.out.println("5 - Listar todas as ocorrências");
        System.out.println("0 - Voltar");
        System.out.println("Escolha: ");
    }

    private void processarOpcao(int opc) {
        switch (opc) {
            case 1 -> menuRegistrarAtraso();
            case 2 -> menuRegistrarAvaria();
            case 3 -> menuQuitarOcorrencia();
            case 4 -> menuBuscarPorContrato();
            case 5 -> menuListarTodas();
            case 0 -> System.out.println("Voltando...");
            default -> System.out.println("Opção inválida.");
        }
    }

    private void menuRegistrarAtraso() {
        boolean sucesso = false;
        while (!sucesso) {
            try {
                System.out.println("ID do contrato:");
                long idContrato = Long.parseLong(entrada.nextLine().trim());

                System.out.println("Data de devolução real (AAAA-MM-DD):");
                LocalDate dataDevolucaoReal = LocalDate.parse(entrada.nextLine().trim());

                System.out.println("Quantidade de dias de atraso:");
                long diasAtraso = Long.parseLong(entrada.nextLine().trim());

                System.out.println("Valor da taxa diária do(s) produto(s) (R$):");
                BigDecimal valorDiaria = new BigDecimal(entrada.nextLine().trim().replace(",", "."));

                facade.registrarAtraso(idContrato, dataDevolucaoReal, diasAtraso, valorDiaria);
                System.out.println("Atraso registrado com sucesso.");
                sucesso = true;

            } catch (DateTimeParseException e) {
                System.err.println("Formato de data inválido. Use AAAA-MM-DD.");
            } catch (NumberFormatException e) {
                System.err.println("Valor numérico inválido.");
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: " + e.getMessage());
                sucesso = true;
            }
        }
    }

    private void menuRegistrarAvaria() {
        boolean sucesso = false;
        while (!sucesso) {
            try {
                System.out.println("ID do contrato:");
                long idContrato = Long.parseLong(entrada.nextLine().trim());

                System.out.println("Descrição da avaria:");
                String descricao = entrada.nextLine().trim();

                facade.registrarAvaria(idContrato, descricao);
                System.out.printf("Avaria registrada. Multa: R$ %s%n", Ocorrencia.TAXA_AVARIA);
                sucesso = true;

            } catch (NumberFormatException e) {
                System.err.println("ID inválido.");
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: " + e.getMessage());
                sucesso = true;
            }
        }
    }

    private void menuQuitarOcorrencia() {
        boolean sucesso = false;
        while (!sucesso) {
            try {
                System.out.println("ID do contrato da ocorrência a quitar:");
                long idContrato = Long.parseLong(entrada.nextLine().trim());

                facade.quitarOcorrencia(idContrato);
                System.out.println("Ocorrência quitada com sucesso.");
                sucesso = true;

            } catch (NumberFormatException e) {
                System.err.println("ID inválido.");
            } catch (IllegalArgumentException e) {
                System.err.println("Erro: " + e.getMessage());
                sucesso = true;
            }
        }
    }

    private void menuBuscarPorContrato() {
        try {
            System.out.println("ID do contrato:");
            long idContrato = Long.parseLong(entrada.nextLine().trim());

            Ocorrencia ocorrencia = facade.buscarOcorrenciaPorContrato(idContrato);

            if (ocorrencia == null) {
                System.out.println("Nenhuma ocorrência encontrada para este contrato.");
                return;
            }

            System.out.printf("Contrato: %d | Tipo: %s | Data: %s | Descrição: %s | Multa: R$ %s | Quitada: %s%n",
                    ocorrencia.getIdContrato(), ocorrencia.getTipo(),
                    ocorrencia.getDataOcorrencia(), ocorrencia.getDescricao(),
                    ocorrencia.getValorMulta(), ocorrencia.isQuitada() ? "Sim" : "Não");

        } catch (NumberFormatException e) {
            System.err.println("ID inválido.");
        }
    }

    private void menuListarTodas() {
        Map<Long, Ocorrencia> todas = facade.listarOcorrencias();

        if (todas.isEmpty()) {
            System.out.println("Nenhuma ocorrência registrada.");
            return;
        }

        System.out.println("\n--- Ocorrências ---");
        for (Ocorrencia o : todas.values()) {
            System.out.printf("Contrato: %d | Tipo: %s | Data: %s | Multa: R$ %s | Quitada: %s%n",
                    o.getIdContrato(), o.getTipo(), o.getDataOcorrencia(),
                    o.getValorMulta(), o.isQuitada() ? "Sim" : "Não");
        }
    }
}