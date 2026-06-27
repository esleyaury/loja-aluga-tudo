package com.upe.loja;

import com.upe.loja.UI.MenuCategoria;
import com.upe.loja.business.CategoriaBusiness;
import com.upe.loja.repository.CategoriaRepository;

public class Main{
  public static void main(String[] args) {
    CategoriaRepository categoriaRepository= new CategoriaRepository();
    CategoriaBusiness categoriaBusiness = new CategoriaBusiness(categoriaRepository);
    Facade facade = new Facade(categoriaBusiness);

    MenuCategoria menuCategoria = new MenuCategoria(facade);
    menuCategoria.iniciar();
  }
}

