package com.upe.loja.UI;
import com.upe.loja.Facade;
import com.upe.loja.repository.entity.Produto;

import java.util.Scanner;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Menu{
    private Scanner entrada;
    private int opc;
    private Facade facade;

    public Menu(Facade facade){
        this.entrada = new Scanner(System.in);
        this.opc = -1;
        this.facade = facade;
    }

    public void iniciar(){
        
        do{
            exibirOpcoes();
            String digitado = entrada.nextLine();
            try{
                opc = Integer.parseInt(digitado);
                processarOpcao(opc);
            } catch(NumberFormatException e){
                System.out.println("Entrada Inválida! Por favor, digite um número.");
            }
        } while (opc != 0) ;
    }

    private void exibirOpcoes(){
        System.out.println("------ Menu da Loja MENOB's -----");
        System.out.println("Escolha uma opção: ");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Listar Produtos");
        System.out.println("3 - Atualizar Produto");
        System.out.println("4 - Remover");
        System.out.println("0 - Sair");
        System.out.println("Digite sua escolha abaixo: ");
    }

    public void processarOpcao(int opc){
        switch(opc){
            case 1:
                // Cadastrar novo produto
                // Instancia um novo produto, e salva ele
                menuCadastrarProduto();
                break;
            case 2:
                menuListarProdutos();
                break;
            case 3:
                menuAtualizarProduto();
                break;
            case 4:
                menuRemoverProduto();
                break;

            case 0:
                facade.fecharPrograma();
                break;
            default:
                System.out.println("Opção Invalida!!!");
        }
    }

    public void menuCadastrarProduto(){
      System.out.println("Digite: id, nome, taxaDiaria, conservacao, valorReposicao");
      Scanner scanner = new Scanner(System.in);
      String linha = scanner.nextLine();
      //scanner.close();
      String[] partes = linha.split(",");
      String id = partes[0].trim();
      String nome = partes[1].trim();
      BigDecimal taxaDiaria = new BigDecimal(partes[2].trim());
      String conservacao = partes[3].trim();
      BigDecimal valorReposicao = new BigDecimal(partes[4].trim());
      facade.cadastrarProduto(id, nome, taxaDiaria, conservacao, valorReposicao);
    }

    public void menuListarProdutos(){
        Map<String, Produto> produtos = facade.listarTodos();
        for (Produto p : produtos.values()){
            System.out.println(p);
        }
    }

    public void menuAtualizarProduto(){
        System.out.println("Digite o ID do produto que deseja atualizar:\n");
        Scanner scanner = new Scanner (System.in);
        String id = scanner.nextLine();
        Produto produtoEncontrado = facade.buscarPorId(id);
        System.out.println("O que deseja alterar? \n 1- NOME 2-TAXA 3-CONSERVACAO 4-ESTADO\n");
        int option = scanner.nextInt();
        scanner.nextLine();
        System.out.println("O que deseja inserir no lugar?\n");
        String valor = scanner.nextLine();
        //scanner.close();
        facade.atualizarProduto(produtoEncontrado, option, valor);
    }

    public void menuRemoverProduto(){
        System.out.println("Digite o ID do produto que deseja deletar:\n");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        //scanner.close();
        Produto produtoEncontrado = facade.buscarPorId(id);
        String idProduto = produtoEncontrado.getID();
        facade.removerProduto(idProduto);
    }
}
