package com.upe.loja.business.interfaces;

import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Contrato;

public interface IContratoBusiness {

    public void salvarContrato();

    public void cadastrarContrato();

    public Map<Long, Contrato> listarContratos();

    public Contrato buscarPorId();

    public List<Long> buscarAtivos();

    public List<Long> bucarPorCpfCliente();

    public void atualizarContrato();

    public void guardarDados();

}