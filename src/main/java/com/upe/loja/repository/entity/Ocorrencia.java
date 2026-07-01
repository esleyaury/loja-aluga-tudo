package com.upe.loja.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Ocorrencia {

    // Constantes de multa — altere aqui conforme necessário
    public static final BigDecimal MULTA_FIXA = new BigDecimal("50.00");
    public static final BigDecimal PERCENTUAL_DIA_ATRASO = new BigDecimal("0.05"); // 5% por dia
    public static final BigDecimal TAXA_AVARIA = new BigDecimal("100.00");

    private final long idContrato;
    private final TipoOcorrencia tipo;
    private final LocalDate dataOcorrencia;
    private final String descricao;
    private final BigDecimal valorMulta;
    private boolean quitada;

    public enum TipoOcorrencia {
        ATRASO,
        AVARIA
    }

    public Ocorrencia(long idContrato, TipoOcorrencia tipo, LocalDate dataOcorrencia, String descricao, BigDecimal valorMulta) {

        if (descricao == null || descricao.isBlank())
            throw new IllegalArgumentException("Descrição é obrigatória.");
        if (valorMulta == null || valorMulta.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Valor da multa não pode ser negativo.");

        this.idContrato = idContrato;
        this.tipo = tipo;
        this.dataOcorrencia = dataOcorrencia;
        this.descricao = descricao;
        this.valorMulta = valorMulta;
        this.quitada = false;
    }

    // Construtor para reconstrução via CSV
    public Ocorrencia(long idContrato, TipoOcorrencia tipo, LocalDate dataOcorrencia, String descricao, BigDecimal valorMulta, boolean quitada) {
        this(idContrato, tipo, dataOcorrencia, descricao, valorMulta);
        this.quitada = quitada;
    }

    public long getIdContrato() { return this.idContrato; }
    public TipoOcorrencia getTipo() { return this.tipo; }
    public LocalDate getDataOcorrencia(){ return this.dataOcorrencia; }
    public String getDescricao(){ return this.descricao; }
    public BigDecimal getValorMulta() { return this.valorMulta; }
    public boolean isQuitada(){ return this.quitada; }

    public void quitar() {
        this.quitada = true;
    }

    // Calcula multa por atraso: valor fixo + (percentual * diasAtraso * valorDiaria)
    public static BigDecimal calcularMultaAtraso(long diasAtraso, BigDecimal valorDiaria) {
        BigDecimal percentualTotal = PERCENTUAL_DIA_ATRASO.multiply(new BigDecimal(diasAtraso)).multiply(valorDiaria);
        return MULTA_FIXA.add(percentualTotal);
    }

    public static BigDecimal calcularMultaAvaria() {
        return TAXA_AVARIA;
    }
}