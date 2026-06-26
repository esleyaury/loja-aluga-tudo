package com.upe.loja.UI;
import com.upe.loja.Facade;
import com.upe.loja.repository.entity.Produto;

import java.util.Scanner;
import java.math.BigDecimal;
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
            } catch(Exception e){
                System.out.println("Erro na operação: " + e.getMessage() + "\n");
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
                entrada.close();
                break;
            default:
                System.out.println("Opção Invalida!!!");
        }
    }

    public void menuCadastrarProduto(){
      System.out.println("Digite: id, nome, taxaDiaria, conservacao, valorReposicao");
      String linha = entrada.nextLine();
      String[] partes = linha.split(",");
      if (partes.length < 5) {
        throw new IllegalArgumentException("Formato inválido. Use vírgulas para separar os 5 campos.");
      }
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
            System.out.println("-----------------------------------");
            System.out.println("ID: " + p.getID());
            System.out.println("Nome: " + p.getNome());
            System.out.println("Taxa Diária: R$ " + p.getTaxaDiaria());
            System.out.println("Conservação: " + p.getConservacao());
            System.out.println("Valor Reposição: R$ " + p.getValorReposicao());
            System.out.println("Estado: " + p.getEstado());
        }
        System.out.println("-----------------------------------");
    }

    public void menuAtualizarProduto(){
        System.out.println("Digite o ID do produto que deseja atualizar:\n");
        String id = entrada.nextLine();
        Produto produtoEncontrado = facade.buscarPorId(id);
        System.out.println("O que deseja alterar? \n 1- NOME 2-TAXA 3-CONSERVACAO 4-ESTADO\n");
        int option = entrada.nextInt();
        entrada.nextLine();
        System.out.println("O que deseja inserir no lugar?\n");
        String valor = entrada.nextLine();
        facade.atualizarProduto(produtoEncontrado, option, valor);
    }

    public void menuRemoverProduto(){
        System.out.println("Digite o ID do produto que deseja deletar:\n");
        String id = entrada.nextLine();
        Produto produtoEncontrado = facade.buscarPorId(id);
        String idProduto = produtoEncontrado.getID();
        facade.removerProduto(idProduto);
    }
}
