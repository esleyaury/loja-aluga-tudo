package com.upe.loja.business;

import com.upe.loja.business.interfaces.IProdutoBusiness;
import com.upe.loja.repository.ProdutoRepository;
// [CORREÇÃO ANTIGRAVITY] Adicionado import da interface IProdutoRepository.
// A camada de negócio deve interagir com a persistência apenas pela interface.
import com.upe.loja.repository.interfaces.IProdutoRepository;
import com.upe.loja.repository.entity.Produto;
import com.upe.loja.repository.entity.Produto.EstadoProduto;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.Map;

public class ProdutoBusiness implements IProdutoBusiness{
  // [CORREÇÃO ANTIGRAVITY] Tipo do campo alterado de ProdutoRepository (concreto)
  // para IProdutoRepository (interface). Cada camada só deve conhecer a interface
  // da camada abaixo, nunca a implementação concreta.
  private IProdutoRepository estoque;

  // [CORREÇÃO ANTIGRAVITY] Construtor alterado: antes recebia ProdutoRepository
  // por parâmetro (fazendo a Main conhecer todas as camadas). Agora cria o
  // repository internamente, seguindo o Facade Pattern do projeto onde cada
  // camada encapsula a instanciação da camada abaixo.
  public ProdutoBusiness(){
    this.estoque = new ProdutoRepository();
  }

  // [CORREÇÃO ANTIGRAVITY] Novo método: a criação do objeto Produto foi movida
  // para cá (antes estava no Facade). A camada de negócio é a responsável por
  // criar entidades e aplicar regras de validação, não a Facade.
  @Override
  public void cadastrarProduto(String id, String nome, BigDecimal taxaDiaria,
      String conservacao, BigDecimal valorReposicao){
      Produto produto = new Produto(id, nome, taxaDiaria, conservacao,
          valorReposicao, Produto.EstadoProduto.valueOf("DISPONIVEL"));
      salvar(produto);
  }

  @Override
    public List<Produto> verificarDisponibilidade(String nome){
      List<Produto> listaEstoque = this.estoque.buscarProduto(nome);

      if (listaEstoque.isEmpty()){
        return new ArrayList<>();
      }

      List<Produto> disponiveis = new ArrayList<>();

      for (Produto produto : listaEstoque){
        if (produto.getEstado() == Produto.EstadoProduto.DISPONIVEL){
          disponiveis.add(produto);
        }
      }
      return disponiveis;
    }

    public Produto buscarPorId(String id){
      return estoque.buscarPorId(id);
  }

    public void salvar(Produto produto){
      // validação estrutural do objeto
      if (produto == null) {
          throw new IllegalArgumentException("O objeto produto não pode ser nulo.");
      }

      // unicidade (Garantir que não sobrescreva um ID já existente)
      if (estoque.buscarPorId(produto.getID()) != null) {
        throw new IllegalArgumentException("Operação negada: Já existe um produto cadastrado com o ID " + produto.getID());
      }

      estoque.salvar(produto);
    }

    public void atualizar(Produto produto, int option, String valor){
      if (produto == null) {
        throw new IllegalArgumentException("Produto inválido para atualização.");
       }
      if (valor == null || valor.trim().isEmpty()) {
        throw new IllegalArgumentException("O novo valor não pode ser vazio.");
      }

      // O switch centralizado na camada Business para validar cada alteração
      switch(option){
        case 1 -> produto.setNome(valor.trim());
        
        case 2 -> {
          try {
            BigDecimal taxa = new BigDecimal(valor.replace(",", "."));
            if (taxa.compareTo(BigDecimal.ZERO) < 0) {
              throw new IllegalArgumentException("A taxa diária não pode ser negativa.");
            }
            produto.setTaxaDiaria(taxa);
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato numérico inválido para a taxa diária.");
          }
        }
        
        case 3 -> produto.setConservacao(valor.trim());
        
        case 4 -> {
          try {
            // Aqui sim a String vira Enum, então tratamos com toUpperCase() e trim()
            EstadoProduto novoEstado = EstadoProduto.valueOf(valor.toUpperCase().trim());
            produto.setEstado(novoEstado);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido. Insira um estado correspondente ao sistema.");
          }
        }
        default -> throw new IllegalArgumentException("Opção de menu inválida para atualização.");
      }
      estoque.atualizar(produto);
    }

    public Map<String, Produto> listarTodos(){
      return estoque.listarTodos();
    }

    public void remover(String id){
       estoque.remover(id);
    }

    public void guardarDados(){
      estoque.guardarDados();
    }

    public Map<String, Produto> produtosDisponiveis(){
      Map<String, Produto> todos = estoque.listarTodos();
      Map <String, Produto> disponiveis = new HashMap<>();
      for (Produto p : todos.values()){
              EstadoProduto disponibilidade = p.getEstado();
              if (disponibilidade == EstadoProduto.DISPONIVEL){
                  disponiveis.put(p.getID(), p);
              }
          }
      return disponiveis;
    }
}
