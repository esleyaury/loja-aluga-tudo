package com.upe.loja;

import com.upe.loja.UI.MenuProduto;

public class Main{

  public static void main(String[] args) {
    Facade facade = new Facade();

    MenuProduto menu = new MenuProduto(facade);
    menu.iniciar();
  }
}
