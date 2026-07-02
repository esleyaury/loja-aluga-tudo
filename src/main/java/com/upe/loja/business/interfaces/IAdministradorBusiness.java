package com.upe.loja.business.interfaces;

import com.upe.loja.repository.entity.Administrador;

import java.util.Map;

public interface IAdministradorBusiness {

    public void cadastrarAdministrador(String cpf, String senha, String nome, String email);
    // cadastrarAdministrador() constroe o objeto 
    // Administrador dentro de business, e chamar salvar(), que chama o 
    // Salvar dentro de Repository

    public Administrador buscarPorCpf(String cpf);
    public Map<String, Administrador> listarTodos();
    public void atualizar(String cpf, int option, String valor);
    public void remover(String cpf);
    public void guardarDados();
    public Administrador autenticar(String cpf, String senha);
}