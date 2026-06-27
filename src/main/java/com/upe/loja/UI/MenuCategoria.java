package com.upe.loja.UI;

import com.upe.loja.Facade;
import java.util.Scanner;
import java.util.Set;

public class MenuCategoria{
    private Scanner entrada;
    private int opc;
    private Facade facade;

    public MenuCategoria(Facade facade){
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
            }catch(NumberFormatException e){
                System.err.println("Entrada inválida");
            }
        } while(opc != 0);
    }

    private void exibirOpcoes(){
        System.out.println("------Menu de categorias------");
        System.out.println("Escolha uma opção: ");
        System.out.println("1 - Cadastrar uma nova categoria");
        System.out.println("2 - Listar categorias");
        System.out.println("3 - Atualizar uma categoria");
        System.out.println("4 - Remover uma categoria");
        System.out.println("0 - Fechar programa");
    }

    public void processarOpcao(int opc){
        switch(opc){
            case 1:
                menuCadastrarCategoria();
                break;
            case 2:
                menuListarCategorias();
                break;
            case 3:
                menuAtualizarCategoria();
                break;
            case 4:
                menuRemoverCategoria();
                break;
            case 0:
                fecharPrograma();
                entrada.close();
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    public void menuCadastrarCategoria(){
        boolean sucesso = false;
        while (!sucesso) {
            System.out.println("Digite o nome da categoria que você deseja cadastrar:");
            String nome = entrada.nextLine().toLowerCase();
            try{
                if (nome == null){
                    System.err.println("O nome deve ser preenchido");
                }
                facade.criarCategoria(nome);
                System.out.println("Categoria criada.");
                sucesso = true;
            }catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }
    
    public void menuListarCategorias(){
        Set<String> categorias = facade.listarCategorias();
        for (String c : categorias){
            System.out.println("---------------------");
            System.out.println(c + "\n");
        }
        System.out.println("---------------------");
    }

    public void menuAtualizarCategoria(){
        System.out.println("Digite o nome da categoria que deseja alterar: ");
        String nomeAntigo = entrada.nextLine().toLowerCase();
        Boolean categoriaEncontrada = facade.buscarCategoria(nomeAntigo);
        if(!categoriaEncontrada){
            System.out.println("Categoria não localizada");
            return;
        }
        
        boolean sucesso = false;
        do{
            try{
                System.out.println("Insira o novo nome da categoria: ");
                String novoNome = entrada.nextLine().toLowerCase();
                facade.atualizarCategoria(nomeAntigo, novoNome);
            } catch(Exception e){
                System.err.println("Erro: " + e.getMessage());
            }
        } while (!sucesso);
    }

    public void menuRemoverCategoria(){
        boolean sucesso = false;
        while(!sucesso){
            System.out.println("Digite o nome da categoria que deseja excluir: ");
            String nome = entrada.nextLine().toLowerCase();
            try{
                Boolean categoriaEncontrada = facade.buscarCategoria(nome);
                if(!categoriaEncontrada){
                    System.out.println("Categoria não localizada");
                    return;
                }
                facade.deletarCategoria(nome);
                System.out.println("Categoria deletada");
                sucesso = true;
            } catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    public void fecharPrograma(){
        facade.fecharPrograma();
    }
}