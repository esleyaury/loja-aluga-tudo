package com.upe.loja.business.interfaces;

import java.util.Set;

public interface ICategoriaBusiness {


    public void deletarCategoria(String nome);
    public void criarCategoria(String nome);
    public Set<String> listarCategorias();
    boolean buscarCategoria(String nome);
    public void guardarDados();
}
