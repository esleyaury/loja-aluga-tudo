package com.upe.loja.repository.interfaces;

import java.util.Set;

public interface ICategoriaRepository {
    void salvar(String nome);
    boolean buscarCategoria(String nome);
    Set<String> listarCategorias();
    void remover(String nome);
    void atualizar(String nomeAtual, String novoNome);
    void guardarDados();
}
