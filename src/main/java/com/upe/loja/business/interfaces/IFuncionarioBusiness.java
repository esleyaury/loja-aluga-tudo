package com.upe.loja.business.interfaces;

import com.upe.loja.repository.entity.Funcionario;
import com.upe.loja.repository.entity.Funcionario.Cargo;

import java.math.BigDecimal;
import java.util.Map;

public interface IFuncionarioBusiness {
    public void cadastrarFuncionario(String id, String cpf, String senha, String nome, String email, BigDecimal salario, Cargo cargo);
    public void salvar(Funcionario funcionario);
    public Funcionario buscarPorId(String id);
    public Funcionario buscarPorCpf(String cpf);
    public Map<String, Funcionario> listarTodos();
    public void atualizar(String id, int option, String valor);
    public void remover(String id);
    public void guardarDados();
}
