package com.upe.loja;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;

import com.upe.loja.business.CategoriaBusiness;
import com.upe.loja.business.ProdutoBusiness;
import com.upe.loja.business.interfaces.ICategoriaBusiness;
import com.upe.loja.business.interfaces.IProdutoBusiness;
import com.upe.loja.repository.entity.Produto;
import com.upe.loja.business.OcorrenciaBusiness;
import com.upe.loja.business.interfaces.IOcorrenciaBusiness;
import com.upe.loja.repository.OcorrenciaRepository;
import com.upe.loja.repository.entity.Ocorrencia;

public class Facade {
    private final IProdutoBusiness produtoBusiness;
    private final ICategoriaBusiness categoriaBusiness;
    private final IOcorrenciaBusiness ocorrenciaBusiness;

    public Facade(){
        this.produtoBusiness = new ProdutoBusiness();
        this.categoriaBusiness = new CategoriaBusiness();
        this.ocorrenciaBusiness = new OcorrenciaBusiness(new OcorrenciaRepository(), new ContratoRepository());
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

    public void registrarAtraso(long idContrato, LocalDate dataDevolucaoReal, long diasAtraso, BigDecimal valorDiaria){
    ocorrenciaBusiness.registrarAtraso(idContrato, dataDevolucaoReal, diasAtraso, valorDiaria);
    }

    public void registrarAvaria(long idContrato, String descricao){
        ocorrenciaBusiness.registrarAvaria(idContrato, descricao);
    }

    public void quitarOcorrencia(long idOcorrencia){
        ocorrenciaBusiness.quitar(idOcorrencia);
    }

    public List<Ocorrencia> buscarOcorrenciasPorContrato(long idContrato){
        return ocorrenciaBusiness.buscarPorContrato(idContrato);
    }

    public boolean clienteTemPendencias(String cpfCliente){
        return ocorrenciaBusiness.clienteTemPendencias(cpfCliente);
    }

    public Map<Long, Ocorrencia> listarOcorrencias(){
        return ocorrenciaBusiness.listarTodas();
    }

    public void removerOcorrencia(long idOcorrencia){
        ocorrenciaBusiness.remover(idOcorrencia);
    }

    public void fecharPrograma(){
        produtoBusiness.guardarDados();
        categoriaBusiness.guardarDados();
        ocorrenciaBusiness.guardarDados();
    }
}
