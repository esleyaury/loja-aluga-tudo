package com.upe.loja;

import com.upe.loja.repository.CategoriaRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaRepositoryTest {

    private CategoriaRepository repository;
    private final File arquivoCategorias = new File("categorias.csv");

    @BeforeEach
    void setup() {
        arquivoCategorias.delete();
        repository = new CategoriaRepository();
    }

    @AfterEach
    void limpar() {
        arquivoCategorias.delete();
    }

    @Test
    void deveSalvarCategoria() {
        repository.salvar("Mobilia");

        assertTrue(repository.buscarCategoria("Mobilia"));
    }

    @Test
    void buscarCategoriaDeveSerCaseInsensitive() {
        repository.salvar("Mobilia");

        assertTrue(repository.buscarCategoria("mobilia"));
        assertTrue(repository.buscarCategoria("MOBILIA"));
    }

    @Test
    void naoDeveEncontrarCategoriaInexistente() {
        assertFalse(repository.buscarCategoria("Inexistente"));
    }

    @Test
    void deveListarTodasAsCategorias() {
        repository.salvar("Mobilia");
        repository.salvar("Eletronico");

        Set<String> todas = repository.listarCategorias();

        assertEquals(2, todas.size());
        assertTrue(todas.contains("mobilia"));
        assertTrue(todas.contains("eletronico"));
    }

    @Test
    void listarCategoriasDeveRetornarCopiaIndependente() {
        repository.salvar("Mobilia");

        Set<String> copia = repository.listarCategorias();
        copia.remove("mobilia");

        assertTrue(repository.buscarCategoria("Mobilia"));
    }

    @Test
    void salvarCategoriaDuplicadaNaoDeveCriarDuasEntradas() {
        repository.salvar("Mobilia");
        repository.salvar("mobilia");

        assertEquals(1, repository.listarCategorias().size());
    }

    @Test
    void deveRemoverCategoria() {
        repository.salvar("Mobilia");

        repository.remover("Mobilia");

        assertFalse(repository.buscarCategoria("Mobilia"));
    }

    @Test
    void removerCategoriaInexistenteNaoDeveLancarErro() {
        assertDoesNotThrow(() -> repository.remover("Inexistente"));
    }

    @Test
    void deveAtualizarCategoria() {
        repository.salvar("Mobilia");

        repository.atualizar("Mobilia", "Moveis");

        assertFalse(repository.buscarCategoria("Mobilia"));
        assertTrue(repository.buscarCategoria("Moveis"));
    }

    @Test
    void deveGuardarDadosNoArquivo() {
        repository.salvar("Mobilia");
        repository.guardarDados();

        assertTrue(arquivoCategorias.exists());

        CategoriaRepository novoRepository = new CategoriaRepository();
        assertTrue(novoRepository.buscarCategoria("Mobilia"));
    }
}