package com.upe.loja.business.interfaces;

import com.upe.loja.repository.entity.Administrador;

import java.util.Map;

public interface IAdministradorBusiness {

    public void cadastrarAdministrador();
    // cadastrarAdministrador() constroe o objeto 
    // Administrador dentro de business, e chamar salvar(), que chama o 
    // Salvar dentro de Repository

    public void salvar(Administrador administrador); // Vai chamar Admin Repository
    public void buscarPorId(String cpf);
    public Map<String, Administrador> listarTodosAdmin();
    public void atualizar(String cpf, int option, String valor);
    public void remover(String cpf);
    public void guardarDados();
}