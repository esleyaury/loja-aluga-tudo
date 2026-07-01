package com.upe.loja.repository.interfaces;

import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Ocorrencia;

public interface IOcorrenciaRepository {
    void salvar(Ocorrencia ocorrencia);
    Ocorrencia buscarPorContrato(long idContrato);
    Map<Long, Ocorrencia> listarTodas();
    List<Ocorrencia> buscarNaoQuitadas();
    void atualizar(Ocorrencia ocorrencia);
    void remover(long idContrato);
    void guardarDados();
}