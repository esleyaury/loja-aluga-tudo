package com.upe.loja.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Contrato.StatusContrato;
import com.upe.loja.repository.interfaces.IContratoRepository;

public class ContratoRepository implements IContratoRepository {

    private Map<Long, Contrato> contratos;
    private GerirContratosCSV gerenciadorArquivo;
    private File arquivoContratos;

    public ContratoRepository() {
        this.arquivoContratos  = new File("contratos.csv");
        this.gerenciadorArquivo = new GerirContratosCSV();
        this.contratos          = gerenciadorArquivo.carregar(this.arquivoContratos);
    }

    @Override
    public void salvar(Contrato contrato) {
        contratos.put(contrato.getId(), contrato);
    }

    @Override
    public Contrato buscarPorId(long id) {
        return contratos.get(id);
    }

    @Override
    public Map<Long, Contrato> listarTodos() {
        return new HashMap<>(contratos);
    }

    @Override
    public List<Contrato> buscarPorCpfCliente(String cpfCliente) {
        List<Contrato> resultado = new ArrayList<>();
        for (Contrato c : contratos.values()) {
            if (c.getCpfCliente().equals(cpfCliente)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    @Override
    public List<Contrato> buscarAtivos() {
        List<Contrato> resultado = new ArrayList<>();
        for (Contrato c : contratos.values()) {
            if (c.getStatus() == StatusContrato.ATIVO) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    @Override
    public void atualizar(Contrato contrato) {
        contratos.put(contrato.getId(), contrato);
    }

    @Override
    public void guardarDados() {
        gerenciadorArquivo.guardarDados(this.arquivoContratos, this.contratos);
    }
}