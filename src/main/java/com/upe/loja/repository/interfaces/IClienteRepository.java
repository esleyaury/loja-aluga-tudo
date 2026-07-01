package com.upe.loja.repository.interfaces;

import java.util.Map;
import com.upe.loja.repository.entity.Cliente;

public interface IClienteRepository {
    public void salvar(Cliente cliente);
    public Cliente buscarPorCpf(String cpf);
    public Map<String, Cliente> listarTodos();
    public void atualizar(Cliente cliente);
    public void remover(String cpf);
    public void guardarDados();
}
