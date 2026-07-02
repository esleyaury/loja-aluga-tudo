package com.upe.loja.repository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upe.loja.repository.entity.Administrador;
import com.upe.loja.repository.interfaces.IAdministradorRepository;

public class AdministradorRepository implements IAdministradorRepository {
    private Map<String, Administrador> administradores;
    private GerirCSV<Administrador> gerenciadorArquivo;
    private File arquivoAdministradores;

    public AdministradorRepository(){
        this.arquivoAdministradores = new File("administradores.csv");
        this.gerenciadorArquivo = new GerirCSV<>();

        // Formato da linha: cpf;senha;nome;email
        List<Administrador> lista = gerenciadorArquivo.carregar(this.arquivoAdministradores, linha -> {
            String[] dados = linha.split(";");
            if (dados.length != 4) { return null; }

            return new Administrador(dados[0], dados[1], dados[2], dados[3]);
        });

        this.administradores = new HashMap<>();
        for (Administrador administrador : lista) {
            this.administradores.put(administrador.getCpf(), administrador);
        }
    }

    @Override
    public void salvar(Administrador administrador){
        administradores.put(administrador.getCpf(), administrador);
    }

    public Map<String, Administrador> listarTodos(){
        return new HashMap<>(administradores);
    }

    public Administrador buscarPorCpf(String cpf){
        return administradores.get(cpf);
    }

    public void atualizar(Administrador administrador){
        administradores.put(administrador.getCpf(), administrador);
    }

    public void remover(String cpf){
        this.administradores.remove(cpf);
    }

    public void guardarDados(){
        gerenciadorArquivo.guardarDados(this.arquivoAdministradores, this.administradores.values(), a ->
            String.format("%s;%s;%s;%s", a.getCpf(), a.getSenha(), a.getNome(), a.getEmail()));
    }
}
