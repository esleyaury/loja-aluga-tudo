package com.upe.loja;

import com.upe.loja.UI.Menu;

public class Main{
  public static void main(String[] args) {
    Facade facade = new Facade();

    Menu menu = new Menu(facade);
    menu.iniciar();
  }
}

