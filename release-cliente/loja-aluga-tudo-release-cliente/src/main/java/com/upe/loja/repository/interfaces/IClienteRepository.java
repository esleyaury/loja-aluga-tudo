package com.upe.loja.repository.interfaces;

import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Cliente;

public interface IClienteRepository {
    public void salvar(Cliente cliente);
    public Cliente buscarPorCpf(long cpf);
    public Map<Long, Cliente> listarTodos();
    public List<Cliente> buscarPorNome(String nome);
    public void atualizar(Cliente cliente);
    public void remover(long cpf);
    public void guardarDados();
}
