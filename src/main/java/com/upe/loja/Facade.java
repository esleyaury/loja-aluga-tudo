package com.upe.loja;

import com.upe.loja.business.ContratoBusiness;
import com.upe.loja.business.OcorrenciaBusiness;
import com.upe.loja.business.interfaces.IContratoBusiness;
import com.upe.loja.business.interfaces.IOcorrenciaBusiness;
import com.upe.loja.repository.ContratoRepository;
import com.upe.loja.repository.OcorrenciaRepository;
import com.upe.loja.repository.ProdutoRepository;
import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Ocorrencia;

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

public class Facade {

    private final IContratoBusiness contratoBusiness;
    private final IOcorrenciaBusiness ocorrenciaBusiness;
    private final IProdutoBusiness produtoBusiness;
    private final ICategoriaBusiness categoriaBusiness;

    public Facade() {
        ContratoRepository contratoRepository    = new ContratoRepository();
        OcorrenciaRepository ocorrenciaRepository = new OcorrenciaRepository();
        ProdutoRepository produtoRepository       = new ProdutoRepository();

        this.contratoBusiness  = new ContratoBusiness(contratoRepository, produtoRepository, ocorrenciaRepository);
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

    // Contrato
    public Contrato criarContrato(String cpfCliente, String cpfFuncionario,
            List<String> idsProdutos, LocalDate dataRetirada, LocalDate dataDevolucaoPrevista) {
        return contratoBusiness.criarContrato(cpfCliente, cpfFuncionario,
                idsProdutos, dataRetirada, dataDevolucaoPrevista);
    }

    public Contrato buscarContratoPorId(long id) {
        return contratoBusiness.buscarPorId(id);
    }

    public List<Contrato> buscarContratosPorCliente(String cpfCliente) {
        return contratoBusiness.buscarPorCpfCliente(cpfCliente);
    }

    public List<Contrato> buscarContratosAtivos() {
        return contratoBusiness.buscarAtivos();
    }


    public Map<Long, Contrato> listarContratos() {
        return contratoBusiness.listarTodos();

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

    public void encerrarContrato(long id) {
        contratoBusiness.encerrarContrato(id);
    }

    // Ocorrencia
    public void registrarAtraso(long idContrato, LocalDate dataDevolucaoReal,
            long diasAtraso, BigDecimal valorDiaria) {
        ocorrenciaBusiness.registrarAtraso(idContrato, dataDevolucaoReal, diasAtraso, valorDiaria);
    }

    public void registrarAvaria(long idContrato, String descricao) {
        ocorrenciaBusiness.registrarAvaria(idContrato, descricao);
    }

    public void quitarOcorrencia(long idContrato) {
        ocorrenciaBusiness.quitar(idContrato);
    }

    public Ocorrencia buscarOcorrenciaPorContrato(long idContrato) {
        return ocorrenciaBusiness.buscarPorContrato(idContrato);
    }

    public boolean clienteTemPendencias(String cpfCliente) {
        return ocorrenciaBusiness.clienteTemPendencias(cpfCliente);
    }

    public Map<Long, Ocorrencia> listarOcorrencias() {
        return ocorrenciaBusiness.listarTodas();
    }

    public void removerOcorrencia(long idContrato) {
        ocorrenciaBusiness.remover(idContrato);
    }

    // Persistência
    public void fecharPrograma() {
        contratoBusiness.guardarDados();
        ocorrenciaBusiness.guardarDados();
        produtoBusiness.guardarDados();
        categoriaBusiness.guardarDados();
        ocorrenciaBusiness.guardarDados();
    }
}