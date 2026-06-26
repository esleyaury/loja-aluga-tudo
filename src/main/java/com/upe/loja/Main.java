package com.upe.loja;

import com.upe.loja.UI.*;
import com.upe.loja.business.ProdutoBusiness;
import com.upe.loja.repository.ProdutoRepository;
public class Main{
  public static void main(String[] args) {  
    ProdutoRepository repository = new ProdutoRepository();
    ProdutoBusiness business = new ProdutoBusiness(repository);
    Facade facade = new Facade(business);

    Menu menu = new Menu(facade);
    menu.iniciar();

  }
}

