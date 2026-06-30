package com.upe.loja.repository.interfaces;

import java.util.Map;
import com.upe.loja.repository.entity.Funcionario;

public interface IFuncionarioRepository {
    public void salvar(Funcionario funcionario);
    public Funcionario buscarPorId(String id);
    public Funcionario buscarPorCpf(String cpf);
    public Map<String, Funcionario> listarTodos();
    public void atualizar(Funcionario funcionario);
    public void remover(String id);
    public void guardarDados();
}