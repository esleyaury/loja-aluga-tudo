package com.upe.loja.UI;
import com.upe.loja.Facade;
import com.upe.loja.repository.entity.Produto;

// [CORREÇÃO ANTIGRAVITY] Adicionado import de InputMismatchException para tratar
// entrada inválida no menuAtualizarProduto (requisito: "tratar entradas inválidas").
import java.util.InputMismatchException;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.Map;

public class MenuProduto{
    private Scanner entrada;
    private int opc;
    private Facade facade;

    public MenuProduto(Facade facade){
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
            //criar case 5 para a gerar relatórios, pensar nisso

            case 0:
                facade.fecharPrograma();
                entrada.close();
                break;
            default:
                System.out.println("Opção Invalida!!!");
        }
    }

    public void menuCadastrarProduto(){
        boolean sucesso = false;
        while(!sucesso){
            System.out.println("Digite: id, nome, categoria, taxaDiaria, conservacao, valorReposicao");
            String linha = entrada.nextLine();
            try{
                String[] partes = linha.split(",");
                if (partes.length < 6) {
                    throw new IllegalArgumentException("Formato inválido. Use vírgulas para separar os 5 campos.");
                }
                String id = partes[0].trim();
                String nome = partes[1].trim();
                String categoria = partes[2].trim();
                BigDecimal taxaDiaria = new BigDecimal(partes[3].trim());
                String conservacao = partes[4].trim();
                BigDecimal valorReposicao = new BigDecimal(partes[5].trim());
                facade.cadastrarProduto(id, nome, categoria, taxaDiaria, conservacao, valorReposicao);
                System.out.println("Produto cadastrado.");
                sucesso = true;
            }catch(NumberFormatException e){
                System.err.println("Taxa e/ou valor estão fora do formato");
            } catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            }
        } 
    }

    public void menuListarProdutos(){
        Map<String, Produto> produtos = facade.listarTodos();
        for (Produto p : produtos.values()){
            System.out.println("-----------------------------------");
            System.out.println("ID: " + p.getID());
            System.out.println("Nome: " + p.getNome());
            System.out.println("Categoria: " + p.getCategoria());
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
        if(produtoEncontrado == null){
            System.out.println("Produto não localizado");
            return;
        }
        
        boolean sucesso = false;

        do{
            try{
                System.out.println("O que deseja alterar? \n 1- NOME 2-TAXA 3-CONSERVACAO 4-ESTADO\n");
                int option = entrada.nextInt();
                entrada.nextLine();
                System.out.println("O que deseja inserir no lugar?\n");
                String valor = entrada.nextLine();
                facade.atualizarProduto(produtoEncontrado, option, valor);
                // [CORREÇÃO ANTIGRAVITY] Mensagem corrigida: antes dizia "Produto cadastrado"
                // em vez de "Produto atualizado". Bug de copy-paste.
                System.out.println("Produto atualizado.");
                sucesso = true;
            }catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage() + "\n Tente novamente\n");
            // [CORREÇÃO ANTIGRAVITY] Adicionado tratamento de InputMismatchException.
            // Sem isso, se o usuário digitar uma letra em vez de número na opção,
            // o programa lança exceção não tratada (requisito: "tratar entradas inválidas").
            }catch(InputMismatchException e){
                System.err.println("Entrada inválida! Digite um número.\n");
                entrada.nextLine();
            }
        }while (!sucesso);
        
    }

    public void menuRemoverProduto(){
        boolean sucesso = false;
        while(!sucesso){
            System.out.println("Digite o ID do produto que deseja deletar:\n");
            String id = entrada.nextLine();
            try{
                Produto produtoEncontrado = facade.buscarPorId(id);
                if (produtoEncontrado == null){
                    throw new IllegalArgumentException("Produto não encontrado");
                }
                String idProduto = produtoEncontrado.getID();
                facade.removerProduto(idProduto);
                System.out.println("Produto removido.");
                sucesso = true;
            }catch(IllegalArgumentException e){
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }
}
