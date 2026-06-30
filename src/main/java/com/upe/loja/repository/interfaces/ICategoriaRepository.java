package com.upe.loja.repository.interfaces;

import java.util.Set;

public interface ICategoriaRepository {
    void salvar(String nome);
    boolean existe(String nome);
    Set<String> listarTodas();
    void remover(String nome);
    void atualizar(String nomeAtual, String novoNome);
    void guardarDados();
}
