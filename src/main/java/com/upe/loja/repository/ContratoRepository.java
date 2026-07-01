package com.upe.loja.repository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Contrato.StatusContrato;
import com.upe.loja.repository.interfaces.IContratoRepository;

public class ContratoRepository implements IContratoRepository{
    private Map<Long, Contrato> contratos;
    private GerirContratosCSV gerenciadorArquivo;
    private File arquivoProdutos;

    public ContratoRepository(){
        this.arquivoProdutos = new File("contratos.csv");
        this.gerenciadorArquivo = new GerirContratosCSV();
        this.contratos = gerenciadorArquivo.carregar(arquivoProdutos);
    }

    @Override
    public void salvar(Contrato contrato){
        contratos.put(contrato.getId(), contrato);
    }

    @Override
    public Contrato buscarPorId(long id){
        return contratos.get(id);
    }

    @Override
    public Map<Long, Contrato> listarTodos(){
        return new HashMap<>(contratos);
    }

    @Override
    public List<Contrato> buscarPorCpfCliente(String cpfCliente){
        return this.contratos.values().stream().filter(p -> p.getCpfCliente().equals(cpfCliente.trim()))
        .collect(Collectors.toList());
    }

    @Override
    public List<Contrato> buscarAtivos() {
    return this.contratos.values().stream()
        .filter(p -> p.getStatus() == StatusContrato.ATIVO)
        .collect(Collectors.toList());
    } 

    @Override
    public void atualizar(Contrato contrato){}

    @Override
    public void guardarDados(){
        gerenciadorArquivo.guardarDados(this.arquivoProdutos, contratos);
    }

}
