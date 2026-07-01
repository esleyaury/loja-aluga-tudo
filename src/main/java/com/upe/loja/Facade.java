package com.upe.loja;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.upe.loja.business.CategoriaBusiness;
import com.upe.loja.business.ProdutoBusiness;
import com.upe.loja.business.interfaces.ICategoriaBusiness;
import com.upe.loja.business.interfaces.IProdutoBusiness;
import com.upe.loja.repository.entity.Produto;
import com.upe.loja.repository.entity.Funcionario;
import com.upe.loja.repository.entity.Funcionario.Cargo;

import com.upe.loja.business.interfaces.IFuncionarioBusiness;
import com.upe.loja.business.FuncionarioBusiness;
import com.upe.loja.business.ClienteBusiness;
import com.upe.loja.business.interfaces.IClienteBusiness;
import com.upe.loja.repository.entity.Cliente;

public class Facade {
    private final IProdutoBusiness produtoBusiness;
    private final ICategoriaBusiness categoriaBusiness;
    private final IClienteBusiness clienteBusiness;
    private final IFuncionarioBusiness funcionarioBusiness;

    public Facade(){
        this.produtoBusiness = new ProdutoBusiness();
        this.categoriaBusiness = new CategoriaBusiness();
        this.clienteBusiness = new ClienteBusiness();
        this.funcionarioBusiness = new FuncionarioBusiness();
    }

    public void cadastrarProduto(String id, String nome, String categoria, BigDecimal taxaDiaria,
        String conservacao, BigDecimal valorReposicao){
        produtoBusiness.cadastrarProduto(id, nome, categoria, taxaDiaria, conservacao, valorReposicao);
    }

    public List<Produto> verificarDisponibilidade(String nome){
        return produtoBusiness.verificarDisponibilidade(nome);
    }

    public Produto buscarPorId(String id){
        return produtoBusiness.buscarPorId(id);
    }

    public void atualizarProduto(Produto produto, int option, String valor){
        produtoBusiness.atualizar(produto, option, valor);
    }

    public Map<String, Produto> listarTodos(){
        return produtoBusiness.listarTodos();
    }

    public void removerProduto(String id){
        produtoBusiness.remover(id);
    }
    
    public void criarCategoria(String nome){
        categoriaBusiness.criarCategoria(nome);
    }

    public Set<String> listarCategorias(){
        return categoriaBusiness.listarCategorias();
    }

    public void deletarCategoria(String nome){
        categoriaBusiness.deletarCategoria(nome);
    }

    public boolean buscarCategoria(String nome){
        return categoriaBusiness.buscarCategoria(nome);
    }

    public void atualizarCategoria(String nomeAntigo, String nomeNovo){
        categoriaBusiness.atualizar(nomeAntigo, nomeNovo);
    }

    public void fecharPrograma(){
        produtoBusiness.guardarDados();
        categoriaBusiness.guardarDados();
        clienteBusiness.guardarDados();
        funcionarioBusiness.guardarDados();
    }

    public void cadastrarCliente(String cpf, String senha, String nome, String email) {
        clienteBusiness.cadastrarCliente(cpf, senha, nome, email);
    }

    public void atualizarCliente(String cpf, int option, String valor) {
        clienteBusiness.atualizar(cpf, option, valor);
    }

    public Map<String, Cliente> listarTodosCliente() {
        return clienteBusiness.listarTodos();
    }

    public Cliente buscarPorCpf(String cpf) {
        return clienteBusiness.buscarPorCpf(cpf);
    } //so ta aq porque tem uma função para pesquisar cliente

    public void removerCliente(String cpf) {
        clienteBusiness.remover(cpf);
    }

    public boolean podeAlugar(String cpf) {
        return clienteBusiness.podeAlugar(cpf);
    }

    public void cadastrarFuncionario(String cpf, String senha, String nome, String email, BigDecimal salario, Cargo cargo){
        funcionarioBusiness.cadastrarFuncionario(cpf, senha, nome, email, salario, cargo);
    }

    public Map<String, Funcionario> listarTodosFuncionario(){
        return funcionarioBusiness.listarTodos();
    }

    public void atualizarFuncionario(String cpf, int option, String valor){
        funcionarioBusiness.atualizar(cpf, option, valor);
    }

    public void removerFuncionario(String cpf){
        funcionarioBusiness.remover(cpf);
    }
    
}
