package com.upe.loja.business;
import com.upe.loja.business.interfaces.IAdministradorBusiness;
import com.upe.loja.repository.interfaces.IAdministradorRepository;
import com.upe.loja.repository.AdministradorRepository;
import com.upe.loja.repository.entity.Administrador;

import java.util.Map;

// Gerenciar funcionario


public class AdministradorBusiness implements IAdministradorBusiness{
    private IAdministradorRepository administradores;

    public AdministradorBusiness(){
        this.administradores = new AdministradorRepository();
    }

    public void cadastrarAdministrador(String cpf, String senha, String nome, 
        String email){
            Administrador admin = new Administrador(email, cpf, senha, nome, email);
            administradores.salvar(admin);
    }

    public Administrador buscarPorCpf (String cpf){
        return administradores.buscarPorCpf(cpf);
    }

    public Map<String, Administrador> listarTodos(){
        return administradores.listarTodos();
    }

    @Override
    public void atualizar(String cpf, int option, String valor){
        Administrador administrador = buscarPorCpf(cpf);

        switch (option){
            case 1:
                administrador.setCpf(valor);
                break;
            case 2:
                administrador.setEmail(valor);
                break;
            case 3:
                administrador.setNome(valor);
                break;
            case 4:
                administrador.setSenha(valor);
            case 5:
                administrador.setAtivo(converterParaBoolean(valor));
                break;
            default:
                throw new IllegalArgumentException("Opcao Invalida: " + option);
        }
    }

    @Override
    public void remover(String id){
        administradores.remover(id);
    }

    public void guardarDados(){
        administradores.guardarDados();
    }

    public boolean converterParaBoolean(String valor){
        String v = valor.trim().toUpperCase();

        if (v.equals("S") || v.equals("TRUE") || v.equals("1")){
            return true;
        }
        if (v.equals("N") || v.equals("FALSE") || v.equals("0")){
            return false;
        }
        throw new IllegalArgumentException("Valor invalido para ativo: " + valor);
    }
}
