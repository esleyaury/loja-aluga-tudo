package com.upe.loja.business;

import com.upe.loja.business.interfaces.IOcorrenciaBusiness;
import com.upe.loja.repository.OcorrenciaRepository;
import com.upe.loja.repository.ContratoRepository;
import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Ocorrencia;
import com.upe.loja.repository.entity.Ocorrencia.TipoOcorrencia;
import com.upe.loja.repository.interfaces.IOcorrenciaRepository;
import com.upe.loja.repository.interfaces.IContratoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OcorrenciaBusiness implements IOcorrenciaBusiness {

    private final IOcorrenciaRepository ocorrencias;
    private final IContratoRepository contratos;

    public OcorrenciaBusiness(OcorrenciaRepository ocorrencias, ContratoRepository contratos) {
        this.ocorrencias = ocorrencias;
        this.contratos   = contratos;
    }

    @Override
    public void registrarAtraso(long idContrato, LocalDate dataDevolucaoReal, long diasAtraso, BigDecimal valorDiaria) {
        Contrato contrato = contratos.buscarPorId(idContrato);
        if (contrato == null) {
            throw new IllegalArgumentException("Contrato não encontrado.");
        }
        if (diasAtraso <= 0) {
            throw new IllegalArgumentException("Dias de atraso deve ser maior que zero.");
        }

        BigDecimal valorMulta = Ocorrencia.calcularMultaAtraso(diasAtraso, valorDiaria);
        String descricao = "Devolução com " + diasAtraso + " dia(s) de atraso.";

        Ocorrencia ocorrencia = new Ocorrencia(idContrato, TipoOcorrencia.ATRASO,
                dataDevolucaoReal, descricao, valorMulta);

        ocorrencias.salvar(ocorrencia);
    }

    @Override
    public void registrarAvaria(long idContrato, String descricao) {
        Contrato contrato = contratos.buscarPorId(idContrato);
        if (contrato == null) {
            throw new IllegalArgumentException("Contrato não encontrado.");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição da avaria é obrigatória.");
        }

        Ocorrencia ocorrencia = new Ocorrencia(idContrato, TipoOcorrencia.AVARIA,
                LocalDate.now(), descricao, Ocorrencia.calcularMultaAvaria());

        ocorrencias.salvar(ocorrencia);
    }

    @Override
    public void quitar(long idContrato) {
        Ocorrencia ocorrencia = ocorrencias.buscarPorContrato(idContrato);
        if (ocorrencia == null) {
            throw new IllegalArgumentException("Ocorrência não encontrada.");
        }
        if (ocorrencia.isQuitada()) {
            throw new IllegalArgumentException("Ocorrência já foi quitada.");
        }
        ocorrencia.quitar();
        ocorrencias.atualizar(ocorrencia);
    }

    @Override
    public Ocorrencia buscarPorContrato(long idContrato) {
        return ocorrencias.buscarPorContrato(idContrato);
    }

    @Override
    public boolean clienteTemPendencias(String cpfCliente) {
        List<Contrato> contratosCliente = contratos.buscarPorCpfCliente(cpfCliente);
        for (Contrato c : contratosCliente) {
            Ocorrencia ocorrencia = ocorrencias.buscarPorContrato(c.getId());
            if (ocorrencia != null && !ocorrencia.isQuitada()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<Long, Ocorrencia> listarTodas() {
        return ocorrencias.listarTodas();
    }

    @Override
    public void remover(long idContrato) {
        if (ocorrencias.buscarPorContrato(idContrato) == null) {
            throw new IllegalArgumentException("Ocorrência não encontrada.");
        }
        ocorrencias.remover(idContrato);
    }

    @Override
    public void guardarDados() {
        ocorrencias.guardarDados();
    }
}