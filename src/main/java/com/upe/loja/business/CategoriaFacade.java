package com.upe.loja.business;

import com.upe.loja.repository.CategoriaRepository;
import java.util.Set;

public class CategoriaFacade {
    private CategoriaBusiness business;

    public CategoriaFacade() {
        this.business = new CategoriaBusiness(new CategoriaRepository());
    }

    public void criarCategoria(String nome) {
        business.criarCategoria(nome);
    }

    public void deletarCategoria(String nome) {
        business.deletarCategoria(nome);
    }

    public Set<String> listarCategorias() {
        return business.listarCategorias();
    }
}