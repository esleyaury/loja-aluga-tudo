package com.upe.loja;

import com.upe.loja.repository.FornecedorRepository;
import com.upe.loja.repository.entity.Fornecedor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FornecedorRepositoryTest {

    private FornecedorRepository repository;
    private final File arquivoFornecedores = new File("fornecedores.csv");

    @BeforeEach
    void setup() {
        if (arquivoFornecedores.exists()) {
            arquivoFornecedores.delete();
        }
        repository = new FornecedorRepository();
    }

    @AfterEach
    void limpar() {
        if (arquivoFornecedores.exists()) {
            arquivoFornecedores.delete();
        }
    }

    private Fornecedor criarFornecedor(String cnpj, String nome, String telefone) {
        return new Fornecedor(cnpj, nome, telefone);
    }

    @Test
    void deveSalvarFornecedor() {
        Fornecedor fornecedor = criarFornecedor("11.111.111/0001-11", "Empresa A", "81999999999");
        repository.salvar(fornecedor);

        List<Fornecedor> todos = repository.listarTodos();
        assertEquals(1, todos.size());
        assertEquals("Empresa A", todos.get(0).getNome());
    }

    @Test
    void deveListarTodosOsFornecedores() {
        repository.salvar(criarFornecedor("11.111.111/0001-11", "Empresa A", "81999999999"));
        repository.salvar(criarFornecedor("22.222.222/0001-22", "Empresa B", "81888888888"));

        List<Fornecedor> todos = repository.listarTodos();

        assertEquals(2, todos.size());
        assertTrue(todos.stream().anyMatch(f -> f.getCnpj().equals("11.111.111/0001-11")));
        assertTrue(todos.stream().anyMatch(f -> f.getCnpj().equals("22.222.222/0001-22")));
    }

    @Test
    void deveRemoverFornecedor() {
        repository.salvar(criarFornecedor("11.111.111/0001-11", "Empresa A", "81999999999"));
        
        repository.remover("11.111.111/0001-11");

        List<Fornecedor> todos = repository.listarTodos();
        assertTrue(todos.isEmpty());
    }

    @Test
    void removerFornecedorInexistenteNaoDeveLancarErro() {
        assertDoesNotThrow(() -> repository.remover("cnpj_inexistente"));
    }

    @Test
    void deveAtualizarFornecedor() {
        Fornecedor fornecedor = criarFornecedor("11.111.111/0001-11", "Empresa A", "81999999999");
        repository.salvar(fornecedor);

        fornecedor.setNome("Empresa A Atualizada");
        repository.atualizar(fornecedor);

        List<Fornecedor> todos = repository.listarTodos();
        assertEquals("Empresa A Atualizada", todos.get(0).getNome());
    }

    @Test
    void deveGuardarDadosNoArquivoAutomaticamente() {
        repository.salvar(criarFornecedor("11.111.111/0001-11", "Empresa A", "81999999999"));

        assertTrue(arquivoFornecedores.exists());

        // Instancia um NOVO repositório para forçar a leitura do CSV recém-criado
        FornecedorRepository novoRepository = new FornecedorRepository();
        List<Fornecedor> lidos = novoRepository.listarTodos();
        
        assertEquals(1, lidos.size());
        assertEquals("11.111.111/0001-11", lidos.get(0).getCnpj());
        assertEquals("Empresa A", lidos.get(0).getNome());
    }
}