package com.upe.loja.business;

import com.upe.loja.business.interfaces.CategoriaInterface;
import com.upe.loja.repository.CategoriaRepository;
import com.upe.loja.repository.entity.Categoria;
import java.util.Set;

public class CategoriaBusiness implements CategoriaInterface{
    private CategoriaRepository categorias;

    public CategoriaBusiness(CategoriaRepository categorias){
        this.categorias = categorias;
    }
    @Override

   public void criarCategoria(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da categoria não pode ser vazio.");
        }
        if (categorias.buscarCategoria(nome)) {
            throw new IllegalArgumentException("Categoria já existe.");
        }
        Categoria categoria = new Categoria(nome);
        categorias.adicionar(categoria);
        categorias.salvar();
    }

    public Set<String> listarCategorias() {
        return categorias.listarCategorias();
    }

    public void deletarCategoria(String nome) {
        if (!categorias.buscarCategoria(nome)) {
            throw new IllegalArgumentException("Categoria não encontrada.");
        }
        categorias.deletar(nome);
    }
}
