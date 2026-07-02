package com.upe.loja;

import com.upe.loja.repository.entity.Produto;
import com.upe.loja.repository.entity.Produto.EstadoProduto;
import com.upe.loja.repository.ProdutoRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoRepositoryTest {

    private ProdutoRepository repository;
    private final File arquivoProdutos = new File("produtos.csv");

    @BeforeEach
    void setup() {
        arquivoProdutos.delete();
        repository = new ProdutoRepository();
    }

    @AfterEach
    void limpar() {
        arquivoProdutos.delete();
    }

    private Produto criarProduto(String id, String nome) {
        return new Produto(id, nome, "Mobilia", new BigDecimal("10"), "Boa",
                new BigDecimal("100"), EstadoProduto.DISPONIVEL);
    }

    @Test
    void deveSalvarProduto() {
        Produto produto = criarProduto("1", "Cadeira");
        repository.salvar(produto);

        assertNotNull(repository.buscarPorId("1"));
        assertEquals("Cadeira", repository.buscarPorId("1").getNome());
    }

    @Test
    void deveBuscarProdutoPorId() {
        Produto produto = criarProduto("1", "Cadeira");
        repository.salvar(produto);

        Produto encontrado = repository.buscarPorId("1");

        assertEquals(produto, encontrado);
    }

    @Test
    void deveRetornarNullQuandoIdNaoExiste() {
        Produto encontrado = repository.buscarPorId("inexistente");

        assertNull(encontrado);
    }

    @Test
    void deveListarTodosOsProdutos() {
        repository.salvar(criarProduto("1", "Cadeira"));
        repository.salvar(criarProduto("2", "Mesa"));

        Map<String, Produto> todos = repository.listarTodos();

        assertEquals(2, todos.size());
        assertTrue(todos.containsKey("1"));
        assertTrue(todos.containsKey("2"));
    }

    @Test
    void listarTodosDeveRetornarCopiaIndependente() {
        repository.salvar(criarProduto("1", "Cadeira"));

        Map<String, Produto> copia = repository.listarTodos();
        copia.remove("1");

        assertNotNull(repository.buscarPorId("1"));
    }

    @Test
    void deveBuscarProdutoPorNome() {
        repository.salvar(criarProduto("1", "Cadeira"));
        repository.salvar(criarProduto("2", "cadeira"));
        repository.salvar(criarProduto("3", "Mesa"));

        List<Produto> encontrados = repository.buscarProduto("Cadeira");

        assertEquals(2, encontrados.size());
    }

    @Test
    void buscarProdutoPorNomeDeveRetornarVazioQuandoNaoEncontrar() {
        repository.salvar(criarProduto("1", "Cadeira"));

        List<Produto> encontrados = repository.buscarProduto("Inexistente");

        assertTrue(encontrados.isEmpty());
    }

    @Test
    void deveAtualizarProduto() {
        Produto produto = criarProduto("1", "Cadeira");
        repository.salvar(produto);

        produto.setNome("Cadeira Gamer");
        repository.atualizar(produto);

        assertEquals("Cadeira Gamer", repository.buscarPorId("1").getNome());
    }

    @Test
    void deveRemoverProduto() {
        repository.salvar(criarProduto("1", "Cadeira"));

        repository.remover("1");

        assertNull(repository.buscarPorId("1"));
    }

    @Test
    void removerProdutoInexistenteNaoDeveLancarErro() {
        assertDoesNotThrow(() -> repository.remover("inexistente"));
    }

    @Test
    void deveGuardarDadosNoArquivo() {
        repository.salvar(criarProduto("1", "Cadeira"));
        repository.guardarDados();

        assertTrue(arquivoProdutos.exists());

        ProdutoRepository novoRepository = new ProdutoRepository();
        assertNotNull(novoRepository.buscarPorId("1"));
    }
}