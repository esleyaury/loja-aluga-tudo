package com.upe.loja;

<<<<<<< HEAD
public class Main{
  public static void main(String[] args) {
    
  }
}
=======
import com.upe.loja.UI.FornecedorMenu;
>>>>>>> release/ocorrencia

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Iniciando o Sistema ===");
        
        // Instancia e inicia o menu que você acabou de arrumar
        FornecedorMenu menuFornecedor = new FornecedorMenu();
        menuFornecedor.iniciar();
    }
}