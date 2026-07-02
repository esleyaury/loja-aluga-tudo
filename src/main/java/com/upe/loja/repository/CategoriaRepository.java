package com.upe.loja.repository;

import com.upe.loja.repository.interfaces.ICategoriaRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File;

public class CategoriaRepository implements ICategoriaRepository {

    private Set<String> categorias;
    private GerirCSV<String> gerenciadorArquivo;
    private File arquivoCategorias;

    public CategoriaRepository(){
        this.arquivoCategorias = new File("categorias.csv");
        this.gerenciadorArquivo = new GerirCSV<>();

        List<String> lista = gerenciadorArquivo.carregar(this.arquivoCategorias, linha -> linha.trim().toLowerCase());
        this.categorias = new HashSet<>(lista);
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
        gerenciadorArquivo.guardarDados(this.arquivoCategorias, this.categorias, nome -> nome);
    }
}
