package com.upe.loja;

import com.upe.loja.UI.MenuCategoria;

public class Main{
  public static void main(String[] args) {

    Facade facade = new Facade();

    MenuCategoria menuCategoria = new MenuCategoria(facade);
    menuCategoria.iniciar();
  }
}

