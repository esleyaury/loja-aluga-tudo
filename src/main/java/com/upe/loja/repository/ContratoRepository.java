package com.upe.loja.repository;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Contrato.StatusContrato;
import com.upe.loja.repository.interfaces.IContratoRepository;

public class ContratoRepository implements IContratoRepository {

    private Map<Long, Contrato> contratos;
    private GerirCSV<Contrato> gerenciadorArquivo;
    private File arquivoContratos;

    public ContratoRepository() {
        this.arquivoContratos  = new File("contratos.csv");
        this.gerenciadorArquivo = new GerirCSV<>();

        // Formato da linha:
        // id;cpfCliente;cpfFuncionario;idsProdutos(,separados);dataRetirada;dataDevolucaoPrevista;dataDevolucaoReal;valorTotal;status
        List<Contrato> lista = gerenciadorArquivo.carregar(this.arquivoContratos, linha -> {
            String[] dados = linha.split(";");
            if (dados.length < 9) { return null; }

            long id                         = Long.parseLong(dados[0]);
            String cpfCliente               = dados[1];
            String cpfFuncionario           = dados[2];
            Set<String> idsProdutos         = new HashSet<>(Arrays.asList(dados[3].split(",")));
            LocalDate dataRetirada          = LocalDate.parse(dados[4]);
            LocalDate dataDevolucaoPrevista = LocalDate.parse(dados[5]);
            LocalDate dataDevolucaoReal     = dados[6].equals("null") ? null : LocalDate.parse(dados[6]);
            BigDecimal valorTotal           = new BigDecimal(dados[7]);
            StatusContrato status           = StatusContrato.valueOf(dados[8]);

            return new Contrato(id, cpfCliente, cpfFuncionario, idsProdutos,
                    dataRetirada, dataDevolucaoPrevista, dataDevolucaoReal, valorTotal, status);
        });

        this.contratos = new HashMap<>();
        for (Contrato contrato : lista) {
            this.contratos.put(contrato.getId(), contrato);
        }
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
        gerenciadorArquivo.guardarDados(this.arquivoContratos, this.contratos.values(), c -> {
            String produtos      = String.join(",", c.getIdsProdutos());
            String devolucaoReal = c.getDataDevolucaoReal() == null ? "null" : c.getDataDevolucaoReal().toString();

            return String.format("%d;%s;%s;%s;%s;%s;%s;%s;%s",
                    c.getId(),
                    c.getCpfCliente(),
                    c.getCpfFuncionario(),
                    produtos,
                    c.getDataRetirada(),
                    c.getDataDevolucaoPrevista(),
                    devolucaoReal,
                    c.getValorTotal(),
                    c.getStatus());
        });
    }
}
