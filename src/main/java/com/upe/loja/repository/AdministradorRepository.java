package com.upe.loja.repository;

import java.io.*;
import java.util.*;

import com.upe.loja.repository.entity.Administrador;
import com.upe.loja.repository.interfaces.IAdministradorRepository;

public class AdministradorRepository implements IAdministradorRepository {
    private Map<String, Administrador> administradores;
    private GerirAdministradorCSV gerenciadorArquivo;
    private File arquivoAdministradores;

    public AdministradorRepository(){
        this.arquivoAdministradores = new File("administradores.csv");
        this.gerenciadorArquivo = new GerirAdministradorCSV();
        this.administradores = gerenciadorArquivo.carregar(this.arquivoAdministradores);
    }

    public void salvar(Administrador administrador){
        administradores.put(administrador.getId(), administrador);
    }

    public Administrador buscarPorId(String id){
        return administradores.get(id);
    }

    public Map<String, Administrador> listarTodos(){
        return new HashMap<>(administradores);
    }

    public Administrador buscarPorCpf(String cpf){
        return administradores.get(cpf);
    }

    public void atualizar(Administrador administrador){
        administradores.put(administrador.getId(), administrador);
    }

    public void remover(String id){
        this.administradores.remove(id);
    }

    public void guardarDados(){
        gerenciadorArquivo.guardarDados(this.arquivoAdministradores, this.administradores);
    }
}
