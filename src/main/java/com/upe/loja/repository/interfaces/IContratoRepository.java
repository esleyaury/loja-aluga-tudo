package com.upe.loja.repository.interfaces;

import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Contrato;

public interface IContratoRepository {
    void salvar(Contrato contrato);
    Contrato buscarPorId(long id);
    Map<Long, Contrato> listarTodos();
    List<Contrato> buscarPorCpfCliente(String cpfCliente);
    List<Contrato> buscarAtivos();
    void atualizar(Contrato contrato);
    void guardarDados();
}