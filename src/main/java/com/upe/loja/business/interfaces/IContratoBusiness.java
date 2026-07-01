package com.upe.loja.business.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Contrato;

public interface IContratoBusiness {
    Contrato criarContrato(String cpfCliente, String cpfFuncionario,
                           List<String> idsProdutos, LocalDate dataRetirada,
                           LocalDate dataDevolucaoPrevista);
    Contrato buscarPorId(long id);
    List<Contrato> buscarPorCpfCliente(String cpfCliente);
    List<Contrato> buscarAtivos();
    Map<Long, Contrato> listarTodos();
    void encerrarContrato(long id);
    void guardarDados();
}