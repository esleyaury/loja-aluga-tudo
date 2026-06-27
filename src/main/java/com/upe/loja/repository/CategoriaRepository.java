package com.upe.loja.repository;

import java.util.HashSet;
import java.util.Set;
import java.io.File;

public class CategoriaRepository {

    private Set<String> categorias;
    private GerirCategoriasCSV gerenciadorArquivo;
    private File arquivoCategorias;

    public CategoriaRepository(){
        this.arquivoCategorias = new File("categorias.csv");
        this.gerenciadorArquivo = new GerirCategoriasCSV();
        this.categorias = gerenciadorArquivo.carregar(this.arquivoCategorias);
    }

    public void salvar(String nomeCategoria) {
        categorias.add(nomeCategoria.toLowerCase());
    }

    public boolean buscarCategoria(String nomeCategoria) {
        return categorias.contains(nomeCategoria.toLowerCase());
    }

    public Set<String> listarCategorias() {
        return new HashSet<>(categorias);
    }

    public void remover(String nomeCategoria) {
        categorias.remove(nomeCategoria.toLowerCase());
    }

    public void atualizar(String nomeAtual, String novoNome){
        categorias.remove(nomeAtual.toLowerCase());
        categorias.add(novoNome.toLowerCase());
    }

    public void guardarDados(){
        gerenciadorArquivo.guardarDados(this.arquivoCategorias, this.categorias);
    }
}
