package com.upe.loja.business;

import com.upe.loja.business.interfaces.ICategoriaBusiness;
import com.upe.loja.repository.CategoriaRepository;
import java.util.Set;

public class CategoriaBusiness implements ICategoriaBusiness{
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
        categorias.salvar(nome);
    }

    public Set<String> listarCategorias() {
        return categorias.listarCategorias();
    }

    public void deletarCategoria(String nome) {
        if (!categorias.buscarCategoria(nome)) {
            throw new IllegalArgumentException("Categoria não encontrada.");
        }
        categorias.remover(nome);
    }

    public void guardarDados(){
        categorias.guardarDados();
    }
}
