package com.upe.loja.repository;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Ocorrencia;
import com.upe.loja.repository.entity.Ocorrencia.TipoOcorrencia;
import com.upe.loja.repository.interfaces.IOcorrenciaRepository;

public class OcorrenciaRepository implements IOcorrenciaRepository {

    private Map<Long, Ocorrencia> ocorrencias;
    private GerirCSV<Ocorrencia> gerenciadorArquivo;
    private File arquivoOcorrencias;

    public OcorrenciaRepository() {
        this.arquivoOcorrencias = new File("ocorrencias.csv");
        this.gerenciadorArquivo = new GerirCSV<>();

        // Formato da linha: idContrato;tipo;dataOcorrencia;descricao;valorMulta;quitada
        List<Ocorrencia> lista = gerenciadorArquivo.carregar(this.arquivoOcorrencias, linha -> {
            String[] dados = linha.split(";");
            if (dados.length < 6) { return null; }

            long idContrato       = Long.parseLong(dados[0]);
            TipoOcorrencia tipo   = TipoOcorrencia.valueOf(dados[1]);
            LocalDate data        = LocalDate.parse(dados[2]);
            String descricao      = dados[3];
            BigDecimal valorMulta = new BigDecimal(dados[4]);
            boolean quitada       = Boolean.parseBoolean(dados[5]);

            return new Ocorrencia(idContrato, tipo, data, descricao, valorMulta, quitada);
        });

        this.ocorrencias = new HashMap<>();
        for (Ocorrencia ocorrencia : lista) {
            this.ocorrencias.put(ocorrencia.getIdContrato(), ocorrencia);
        }
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
        gerenciadorArquivo.guardarDados(this.arquivoOcorrencias, this.ocorrencias.values(), o ->
            String.format("%d;%s;%s;%s;%s;%b",
                    o.getIdContrato(),
                    o.getTipo(),
                    o.getDataOcorrencia(),
                    o.getDescricao(),
                    o.getValorMulta(),
                    o.isQuitada()));
    }
}
