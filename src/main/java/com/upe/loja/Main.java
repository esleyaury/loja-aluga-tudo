package com.upe.loja;

import com.upe.loja.UI.Menu;

// [CORREÇÃO ANTIGRAVITY] Main simplificada: antes instanciava ProdutoRepository e
// ProdutoBusiness diretamente, violando o requisito do projeto:
// "A UI não deve instanciar regras de negócio ou repositórios diretamente."
// Agora a Main (camada de apresentação) só conhece o Facade e o Menu.
public class Main{
  public static void main(String[] args) {
    Facade facade = new Facade();

    Menu menu = new Menu(facade);
    menu.iniciar();
  }
}
