package com.upe.loja.business.interfaces;

import com.upe.loja.repository.entity.Funcionario;
import java.util.Map;

public interface IFuncionarioBusiness {
    public void salvar(Funcionario funcionario);
    public Funcionario buscarPorId(String id);
    public Funcionario buscarPorCpf(String cpf);
    public Map<String, Funcionario> listarTodos();
    public void atualizar(Funcionario funcionario, int option, String valor);
    public void remover(String id);
    public void guardarDados();
}
