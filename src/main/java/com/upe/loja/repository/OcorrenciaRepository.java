package com.upe.loja.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Ocorrencia;
import com.upe.loja.repository.interfaces.IOcorrenciaRepository;

public class OcorrenciaRepository implements IOcorrenciaRepository {

    private Map<Long, Ocorrencia> ocorrencias;
    private GerirOcorrenciasCSV gerenciadorArquivo;
    private File arquivoOcorrencias;

    public OcorrenciaRepository() {
        this.arquivoOcorrencias = new File("ocorrencias.csv");
        this.gerenciadorArquivo = new GerirOcorrenciasCSV();
        this.ocorrencias        = gerenciadorArquivo.carregar(this.arquivoOcorrencias);
    }

    @Override
    public void salvar(Ocorrencia ocorrencia) {
        ocorrencias.put(ocorrencia.getIdContrato(), ocorrencia);
    }

    @Override
    public Ocorrencia buscarPorContrato(long idContrato) {
        return ocorrencias.get(idContrato);
    }

    @Override
    public Map<Long, Ocorrencia> listarTodas() {
        return new HashMap<>(ocorrencias);
    }

    @Override
    public List<Ocorrencia> buscarNaoQuitadas() {
        List<Ocorrencia> resultado = new ArrayList<>();
        for (Ocorrencia o : ocorrencias.values()) {
            if (!o.isQuitada()) {
                resultado.add(o);
            }
        }
        return resultado;
    }

    @Override
    public void atualizar(Ocorrencia ocorrencia) {
        ocorrencias.put(ocorrencia.getIdContrato(), ocorrencia);
    }

    @Override
    public void remover(long idContrato) {
        ocorrencias.remove(idContrato);
    }

    @Override
    public void guardarDados() {
        gerenciadorArquivo.guardarDados(this.arquivoOcorrencias, this.ocorrencias);
    }
}