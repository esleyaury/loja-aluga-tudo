package com.upe.loja.business.interfaces;

import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Cliente;

public interface IClienteBusiness {
    void cadastrarCliente(long cpf, String senha, String nome, long telefone, String email);
    Cliente buscarPorCpf(long cpf);
    List<Cliente> buscarPorNome(String nome);
    void salvar(Cliente cliente);
    void atualizar(Cliente cliente, int option, String valor);
    Map<Long, Cliente> listarTodos();
    void remover(long cpf);
    void guardarDados();
    Map<Long, Cliente> clientesAtivos();
    boolean podeAlugar(long cpf);
}
