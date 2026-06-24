package com.upe.loja.business;

public class ProdutoFacade{
    private ProdutoBusiness produtoBusiness;

    public ProdutoFacade(){
        this.produtoBusiness = new ProdutoBusiness;
    }
    public void cadastrarProduto(String id, String nome, BigDecimal taxaDiaria,
        String conservacao, BigDecimal valorReposicao, estadoProduto estado){
            Produto produto = new Produto(id,nome,taxaDiaria,conservacao,valorReposicao,estado);
            produtoBusiness.salvar(produto);
        }
    public List<Produto> verificarDisponibilidade(String nome){
        return produtoBusiness.verificarDisponibilidade(nome);
    }
    public void atualizarProduto(Produto produto, int option, String valor){
        produtoBusiness.atualizar(produto, option, valor);
    }
    public void listarProdutos(){
        produtoBusiness.listarTodos();
    }
    public void removerProduto(){
        produtoBusiness.remover(id);
    }
}