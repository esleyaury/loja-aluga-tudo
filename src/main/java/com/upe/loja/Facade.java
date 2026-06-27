package com.upe.loja;

import com.upe.loja.business.CategoriaBusiness;
import java.util.Set;

public class Facade {
    private final CategoriaBusiness categoriaBusiness;

    public Facade(CategoriaBusiness categoriaBusiness){
        this.categoriaBusiness = categoriaBusiness;
    }
    
    public void criarCategoria(String nome){
        categoriaBusiness.criarCategoria(nome);
    }

    public Set<String> listarCategorias(){
        return categoriaBusiness.listarCategorias();
    }

    public void deletarCategoria(String nome){
        categoriaBusiness.deletarCategoria(nome);
    }

    public void fecharPrograma(){
        categoriaBusiness.guardarDados();
    }
}
