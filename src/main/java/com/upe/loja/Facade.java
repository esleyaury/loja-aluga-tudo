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
import com.upe.loja.business.FornecedorBusiness;
import com.upe.loja.business.ProdutoBusiness;
import com.upe.loja.business.AdministradorBusiness;
import com.upe.loja.business.interfaces.ICategoriaBusiness;
import com.upe.loja.business.interfaces.IProdutoBusiness;
import com.upe.loja.business.interfaces.IAdministradorBusiness;
import com.upe.loja.business.interfaces.IFornecedorBusiness;
import com.upe.loja.business.interfaces.IFuncionarioBusiness;
import com.upe.loja.business.FuncionarioBusiness;
import com.upe.loja.business.ClienteBusiness;
import com.upe.loja.business.interfaces.IClienteBusiness;
import com.upe.loja.repository.entity.Produto;
import com.upe.loja.repository.entity.Funcionario;
import com.upe.loja.repository.entity.Funcionario.Cargo;
import com.upe.loja.repository.entity.Administrador;
import com.upe.loja.repository.entity.Fornecedor;
import com.upe.loja.repository.entity.Cliente;
import com.upe.loja.repository.FornecedorRepository;

public class Facade {

    private final IContratoBusiness contratoBusiness;
    private final IOcorrenciaBusiness ocorrenciaBusiness;
    private final IProdutoBusiness produtoBusiness;
    private final ICategoriaBusiness categoriaBusiness;
    private final IClienteBusiness clienteBusiness;
    private final IFuncionarioBusiness funcionarioBusiness;
    private final IAdministradorBusiness administradorBusiness;
    private final IFornecedorBusiness fornecedorBusiness;

    public Facade() {
        ContratoRepository contratoRepository    = new ContratoRepository();
        OcorrenciaRepository ocorrenciaRepository = new OcorrenciaRepository();
        ProdutoRepository produtoRepository       = new ProdutoRepository();

        this.contratoBusiness  = new ContratoBusiness(contratoRepository, produtoRepository, ocorrenciaRepository);
        this.ocorrenciaBusiness = new OcorrenciaBusiness(ocorrenciaRepository, contratoRepository);
        
        this.produtoBusiness = new ProdutoBusiness();
        this.categoriaBusiness = new CategoriaBusiness();
        this.clienteBusiness = new ClienteBusiness();
        this.funcionarioBusiness = new FuncionarioBusiness();
        this.administradorBusiness = new AdministradorBusiness();
        this.fornecedorBusiness = new FornecedorBusiness(new FornecedorRepository());
    }

    // ==================== PRODUTO ====================

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

    public Map<String, Produto> produtosDisponiveis(){
        return produtoBusiness.produtosDisponiveis();
    }

    // ==================== CATEGORIA ====================

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

    // ==================== CLIENTE ====================

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
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteBusiness.buscarPorNome(nome);
    }

    public void removerCliente(String cpf) {
        clienteBusiness.remover(cpf);
    }

    public boolean podeAlugar(String cpf) {
        return clienteBusiness.podeAlugar(cpf);
    }

    public Map<String, Cliente> clientesAtivos() {
        return clienteBusiness.clientesAtivos();
    }

    // ==================== FUNCIONÁRIO ====================

    public void cadastrarFuncionario(String cpf, String senha, String nome, String email, BigDecimal salario, Cargo cargo){
        funcionarioBusiness.cadastrarFuncionario(cpf, senha, nome, email, salario, cargo);
    }

    public Map<String, Funcionario> listarTodosFuncionario(){
        return funcionarioBusiness.listarTodos();
    }

    public Funcionario buscarFuncionarioPorCpf(String cpf){
        return funcionarioBusiness.buscarPorCpf(cpf);
    }

    public void atualizarFuncionario(String cpf, int option, String valor){
        funcionarioBusiness.atualizar(cpf, option, valor);
    }

    public void removerFuncionario(String cpf){
        funcionarioBusiness.remover(cpf);
    }

    // ==================== ADMINISTRADOR ====================

    public void cadastrarAdministrador(String cpf, String senha, String nome, String email){
        administradorBusiness.cadastrarAdministrador(cpf, senha, nome, email);
    }

    public Map<String, Administrador> listarTodosAdministrador(){
        return administradorBusiness.listarTodos();
    }

    public Administrador buscarAdministradorPorCpf(String cpf){
        return administradorBusiness.buscarPorCpf(cpf);
    }

    public void atualizarAdministrador(String cpf, int option, String valor){
        administradorBusiness.atualizar(cpf, option, valor);
    }

    public void removerAdministrador(String cpf){
        administradorBusiness.remover(cpf);
    }

    // ==================== FORNECEDOR ====================

    public void salvarFornecedor(Fornecedor fornecedor){
        fornecedorBusiness.salvar(fornecedor);
    }

    public List<Fornecedor> listarTodosFornecedor(){
        return fornecedorBusiness.listarTodos();
    }

    public void atualizarFornecedor(String cnpj, int opcao, String novoValor){
        fornecedorBusiness.atualizar(cnpj, opcao, novoValor);
    }

    public void removerFornecedor(String cnpj){
        fornecedorBusiness.remover(cnpj);
    }

    // ==================== CONTRATO ====================

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

    public void encerrarContrato(long id) {
        contratoBusiness.encerrarContrato(id);
    }

    // ==================== OCORRENCIA ====================

    public void registrarAtraso(long idContrato, LocalDate dataDevolucaoReal,
            long diasAtraso, BigDecimal valorDiaria) {
        ocorrenciaBusiness.registrarAtraso(idContrato, dataDevolucaoReal, diasAtraso, valorDiaria);
    }

    public void registrarAvaria(long idContrato, String descricao) {
        ocorrenciaBusiness.registrarAvaria(idContrato, descricao);
    }

    public void quitarOcorrencia(long idOcorrencia) {
        ocorrenciaBusiness.quitar(idOcorrencia);
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

    public void removerOcorrencia(long idOcorrencia) {
        ocorrenciaBusiness.remover(idOcorrencia);
    }

    // ==================== SISTEMA ====================

    public void fecharPrograma() {
        contratoBusiness.guardarDados();
        ocorrenciaBusiness.guardarDados();
        produtoBusiness.guardarDados();
        categoriaBusiness.guardarDados();
        clienteBusiness.guardarDados();
        funcionarioBusiness.guardarDados();
        administradorBusiness.guardarDados();
        fornecedorBusiness.guardarDados();
    }
}
