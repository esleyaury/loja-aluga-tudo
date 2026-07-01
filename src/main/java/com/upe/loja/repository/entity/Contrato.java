package com.upe.loja.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Contrato {

    private final long id;
    private final String cpfCliente;
    private final String cpfFuncionario;
    private final Set<String> idsProdutos;
    private final LocalDate dataRetirada;
    private final LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private BigDecimal valorTotal;
    private StatusContrato status;

    public enum StatusContrato {
        ATIVO,
        ENCERRADO
    }

    public Contrato(String cpfCliente, String cpfFuncionario, Set<String> idsProdutos,
                    LocalDate dataRetirada, LocalDate dataDevolucaoPrevista, BigDecimal valorTotal) {

        if (cpfCliente == null || cpfCliente.isBlank())
            throw new IllegalArgumentException("CPF do cliente é obrigatório.");
        if (cpfFuncionario == null || cpfFuncionario.isBlank())
            throw new IllegalArgumentException("CPF do funcionário é obrigatório.");
        if (idsProdutos == null || idsProdutos.isEmpty())
            throw new IllegalArgumentException("O contrato deve ter ao menos um produto.");
        if (dataRetirada == null)
            throw new IllegalArgumentException("Data de retirada é obrigatória.");
        if (dataDevolucaoPrevista == null || dataDevolucaoPrevista.isBefore(dataRetirada))
            throw new IllegalArgumentException("Data de devolução prevista deve ser após a retirada.");
        if (valorTotal == null || valorTotal.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor total deve ser maior que zero.");

        this.id                   = new Random().nextLong(100_000_000L);
        this.cpfCliente           = cpfCliente;
        this.cpfFuncionario       = cpfFuncionario;
        this.idsProdutos          = new HashSet<>(idsProdutos);
        this.dataRetirada         = dataRetirada;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoReal    = null;
        this.valorTotal           = valorTotal;
        this.status               = StatusContrato.ATIVO;
    }

    // Construtor para reconstrução via CSV (id já conhecido)
    public Contrato(long id, String cpfCliente, String cpfFuncionario, Set<String> idsProdutos,
                    LocalDate dataRetirada, LocalDate dataDevolucaoPrevista,
                    LocalDate dataDevolucaoReal, BigDecimal valorTotal, StatusContrato status) {
        this.id                    = id;
        this.cpfCliente            = cpfCliente;
        this.cpfFuncionario        = cpfFuncionario;
        this.idsProdutos           = new HashSet<>(idsProdutos);
        this.dataRetirada          = dataRetirada;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoReal     = dataDevolucaoReal;
        this.valorTotal            = valorTotal;
        this.status                = status;
    }

    public long getId()                          { return this.id; }
    public String getCpfCliente()                { return this.cpfCliente; }
    public String getCpfFuncionario()            { return this.cpfFuncionario; }
    public Set<String> getIdsProdutos()          { return new HashSet<>(this.idsProdutos); }
    public LocalDate getDataRetirada()           { return this.dataRetirada; }
    public LocalDate getDataDevolucaoPrevista()  { return this.dataDevolucaoPrevista; }
    public LocalDate getDataDevolucaoReal()      { return this.dataDevolucaoReal; }
    public BigDecimal getValorTotal()            { return this.valorTotal; }
    public StatusContrato getStatus()            { return this.status; }

    public void setDataDevolucaoReal(LocalDate data) {
        if (data == null)
            throw new IllegalArgumentException("Data de devolução real não pode ser nula.");
        if (data.isBefore(this.dataRetirada))
            throw new IllegalArgumentException("Data de devolução real não pode ser antes da retirada.");
        this.dataDevolucaoReal = data;
    }

    public void setValorTotal(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Valor total deve ser maior que zero.");
        this.valorTotal = valor;
    }

    public void encerrar() {
        this.status = StatusContrato.ENCERRADO;
    }
}