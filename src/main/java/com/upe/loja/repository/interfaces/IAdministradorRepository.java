package com.upe.loja.repository.interfaces;

import java.util.Map;
import com.upe.loja.repository.entity.Administrador;;

public interface IAdministradorRepository {
    public void salvar(Administrador administrador);
    public Administrador buscarPorId(String id);
    public Administrador buscarPorCpf(String cpf);
    public Map<String, Administrador> listarTodos();
    public void atualizar(Administrador administrador);
    public void remover(String id);
    public void guardarDados();
}
