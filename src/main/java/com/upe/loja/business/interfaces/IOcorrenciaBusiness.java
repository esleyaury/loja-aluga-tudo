package com.upe.loja.business.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import com.upe.loja.repository.entity.Ocorrencia;

public interface IOcorrenciaBusiness {
    void registrarAtraso(long idContrato, LocalDate dataDevolucaoReal, long diasAtraso, BigDecimal valorDiaria);
    void registrarAvaria(long idContrato, String descricao);
    void quitar(long idContrato);
    Ocorrencia buscarPorContrato(long idContrato);
    boolean clienteTemPendencias(String cpfCliente);
    Map<Long, Ocorrencia> listarTodas();
    void remover(long idContrato);
    void guardarDados();
}