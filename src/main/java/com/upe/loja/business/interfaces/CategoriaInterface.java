package com.upe.loja.business.interfaces;

import java.util.Set;

public interface CategoriaInterface {


    public void deletarCategoria(String nome);
    public void criarCategoria(String nome);
    public Set<String> listarCategorias();
}
