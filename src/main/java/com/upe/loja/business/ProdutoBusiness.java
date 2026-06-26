package com.upe.loja.business;

import com.upe.loja.business.interfaces.ProdutoInterface;
import com.upe.loja.repository.ProdutoRepository;
import com.upe.loja.repository.entity.Produto;
import com.upe.loja.repository.entity.Produto.EstadoProduto;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Map;

public class ProdutoBusiness implements ProdutoInterface{
  private ProdutoRepository estoque;

  public ProdutoBusiness(ProdutoRepository repository){
    this.estoque = repository; 
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

      //depois ver se essa é a melhor maneira de tratar os erros

      // validação estrutural do objeto
      if (produto == null) {
          throw new IllegalArgumentException("O objeto produto não pode ser nulo.");
      }

      // campos de Texto (Strings) não sejam nulos ou vazios
      if (produto.getID() == null || produto.getID().trim().isEmpty()) {
          throw new IllegalArgumentException("O campo 'ID' é obrigatório e deve ser preenchido.");
      }
      
      if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
          throw new IllegalArgumentException("O campo 'Nome' é obrigatório e deve ser preenchido.");
      }

      if (produto.getConservacao() == null || produto.getConservacao().trim().isEmpty()) {
          throw new IllegalArgumentException("O campo 'Conservação' é obrigatório e deve ser preenchido.");
      }

      //campo de Tipo (Enum) foi selecionado
      if (produto.getEstado() == null) {
          throw new IllegalArgumentException("O campo 'Estado' (Enum) é obrigatório.");
      }

      // valores numéricos (BigDecimal) existem e são MAIORES QUE ZERO
      if (produto.getTaxaDiaria() == null) {
          throw new IllegalArgumentException("O campo 'Taxa Diária' é obrigatório.");
      }
      // compareTo <= 0 significa que o valor é menor ou igual a zero
      if (produto.getTaxaDiaria().compareTo(BigDecimal.ZERO) <= 0) {
          throw new IllegalArgumentException("A 'Taxa Diária' deve ser um valor estritamente maior que zero.");
      }

      if (produto.getValorReposicao() == null) {
          throw new IllegalArgumentException("O campo 'Valor de Reposição' é obrigatório.");
      }
      if (produto.getValorReposicao().compareTo(BigDecimal.ZERO) <= 0) {
          throw new IllegalArgumentException("O 'Valor de Reposição' deve ser um valor estritamente maior que zero.");
      }

      // unicidade (Garantir que não sobrescreva um ID já existente)
      if (estoque.buscarPorId(produto.getID()) != null) {
        throw new IllegalArgumentException("Operação negada: Já existe um produto cadastrado com o ID " + produto.getID());
        }

      // Se passar por todas as barreiras acima, o repositório é finalmente acionado
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
}
