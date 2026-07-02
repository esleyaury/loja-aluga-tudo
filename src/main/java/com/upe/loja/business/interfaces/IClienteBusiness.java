package com.upe.loja.business.interfaces;

import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Cliente;

public interface IClienteBusiness {
    void cadastrarCliente(String cpf, String senha, String nome, String email);
    Cliente buscarPorCpf(String cpf);
    List<Cliente> buscarPorNome(String nome);
    void salvar(Cliente cliente);
    void atualizar(String cliente, int option, String valor);
    Map<String, Cliente> listarTodos();
    void remover(String cpf);
    void guardarDados();
    Map<String, Cliente> clientesAtivos();
    boolean podeAlugar(String cpf);
    Cliente autenticar(String cpf, String senha);
}
