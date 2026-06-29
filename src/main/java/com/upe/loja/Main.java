package com.upe.loja;

import com.upe.loja.UI.MenuFornecedor;

public class Main{
  public static void main(String[] args) {  
    Facade facade = new Facade();

    MenuFornecedor menu = new MenuFornecedor(facade);
    menu.iniciar();
  }
}

