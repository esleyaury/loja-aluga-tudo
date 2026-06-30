package com.upe.loja.business.interfaces;

import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Cliente;

public interface IClienteBusiness {
    void cadastrarCliente(String cpf, String senha, String nome, String email);
    Cliente buscarPorId(String id);
    Cliente buscarPorCpf(String cpf);
    List<Cliente> buscarPorNome(String nome);
    void salvar(Cliente cliente);
    void atualizar(Cliente cliente, int option, String valor);
    Map<String, Cliente> listarTodos();
    void remover(String id);
    void guardarDados();
    Map<String, Cliente> clientesAtivos();
    boolean podeAlugar(String cpf);
}
