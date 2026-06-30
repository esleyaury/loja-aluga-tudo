package com.upe.loja.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upe.loja.repository.entity.Cliente;

/**
 * Testes de unidade da camada de persistência (ClienteRepository), conforme
 * orientação da disciplina: os testes ficam concentrados no Repository,
 * cobrindo operações de CRUD em memória e a correta persistência/recarga
 * a partir do arquivo CSV (clientes.csv).
 *
 * Cada teste recria o arquivo de persistência para não conflitar com dados
 * reais e garantir isolamento entre os casos de teste.
 */
public class ClienteRepositoryTest {

    private static final String ARQUIVO_TESTE = "clientes.csv";
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        new File(ARQUIVO_TESTE).delete();
        clienteRepository = new ClienteRepository();
    }

    @AfterEach
    public void tearDown() {
        new File(ARQUIVO_TESTE).delete();
    }

    @Test
    public void deveSalvarESerEncontradoPorId() {
        Cliente cliente = new Cliente("12345678901", "senha123", "Maria Silva", "maria@email.com");

        clienteRepository.salvar(cliente);

        Cliente encontrado = clienteRepository.buscarPorId(cliente.getId());
        assertNotNull(encontrado);
        assertEquals("Maria Silva", encontrado.getNome());
    }

    @Test
    public void deveSalvarESerEncontradoPorCpf() {
        Cliente cliente = new Cliente("12345678901", "senha123", "Maria Silva", "maria@email.com");

        clienteRepository.salvar(cliente);

        Cliente encontrado = clienteRepository.buscarPorCpf("12345678901");
        assertNotNull(encontrado);
        assertEquals(cliente.getId(), encontrado.getId());
    }

    @Test
    public void buscarPorIdInexistenteDeveRetornarNull() {
        assertNull(clienteRepository.buscarPorId("id-que-nao-existe"));
    }

    @Test
    public void buscarPorCpfInexistenteDeveRetornarNull() {
        assertNull(clienteRepository.buscarPorCpf("00000000000"));
    }

    @Test
    public void deveListarTodosOsClientesSalvos() {
        clienteRepository.salvar(new Cliente("12345678901", "senha123", "Maria Silva", "maria@email.com"));
        clienteRepository.salvar(new Cliente("98765432100", "senha456", "João Souza", "joao@email.com"));

        Map<String, Cliente> todos = clienteRepository.listarTodos();

        assertEquals(2, todos.size());
    }

    @Test
    public void listarTodosNaoDevePermitirAlterarEstadoInternoDoRepositorio() {
        clienteRepository.salvar(new Cliente("12345678901", "senha123", "Maria Silva", "maria@email.com"));

        Map<String, Cliente> copia = clienteRepository.listarTodos();
        copia.clear(); // altera apenas a cópia retornada

        assertEquals(1, clienteRepository.listarTodos().size(), "o mapa interno do repositório não deve ser afetado");
    }

    @Test
    public void deveAtualizarDadosDeUmClienteJaSalvo() {
        Cliente cliente = new Cliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        clienteRepository.salvar(cliente);

        cliente.setNome("Maria Oliveira");
        clienteRepository.atualizar(cliente);

        Cliente atualizado = clienteRepository.buscarPorId(cliente.getId());
        assertEquals("Maria Oliveira", atualizado.getNome());
    }

    @Test
    public void deveRemoverClienteDoRepositorio() {
        Cliente cliente = new Cliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        clienteRepository.salvar(cliente);

        clienteRepository.remover(cliente.getId());

        assertNull(clienteRepository.buscarPorId(cliente.getId()));
    }

    @Test
    public void removerIdInexistenteNaoDeveLancarErro() {
        clienteRepository.remover("id-que-nao-existe");
        // não deve lançar exceção; apenas confirma que a chamada é segura
        assertTrue(clienteRepository.listarTodos().isEmpty());
    }

    @Test
    public void deveGuardarDadosEPermitirRecarregarDoArquivoCsv() {
        Cliente cliente = new Cliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        clienteRepository.salvar(cliente);

        clienteRepository.guardarDados();

        // Simula uma nova execução do programa: nova instância de repositório,
        // que deve carregar os dados persistidos no clientes.csv
        ClienteRepository repositorioRecarregado = new ClienteRepository();
        Cliente recarregado = repositorioRecarregado.buscarPorCpf("12345678901");

        assertNotNull(recarregado, "cliente deveria ter sido recarregado do CSV");
        assertEquals(cliente.getId(), recarregado.getId(), "o id deve ser preservado entre execuções");
        assertEquals("Maria Silva", recarregado.getNome());
        assertEquals("maria@email.com", recarregado.getEmail());
        assertTrue(recarregado.isAtivo());
        assertFalse(recarregado.isInadimplente());
    }

    @Test
    public void deveCriarArquivoCsvVazioQuandoNaoExiste() {
        File arquivo = new File(ARQUIVO_TESTE);
        assertTrue(arquivo.exists(), "o arquivo clientes.csv deve ser criado automaticamente na primeira execução");
    }

    @Test
    public void deveRecarregarEstadoInativoEInadimplenteCorretamenteDoCsv() {
        Cliente cliente = new Cliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        cliente.setAtivo(false);
        cliente.setInadimplente(true);
        clienteRepository.salvar(cliente);
        clienteRepository.guardarDados();

        ClienteRepository repositorioRecarregado = new ClienteRepository();
        Cliente recarregado = repositorioRecarregado.buscarPorCpf("12345678901");

        assertNotNull(recarregado);
        assertFalse(recarregado.isAtivo());
        assertTrue(recarregado.isInadimplente());
    }

    @Test
    public void deveManterMultiplosClientesAoSalvarEGuardarDados() {
        Cliente c1 = new Cliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        Cliente c2 = new Cliente("98765432100", "senha456", "João Souza", "joao@email.com");
        clienteRepository.salvar(c1);
        clienteRepository.salvar(c2);
        clienteRepository.guardarDados();

        ClienteRepository repositorioRecarregado = new ClienteRepository();
        List<Cliente> todos = List.copyOf(repositorioRecarregado.listarTodos().values());

        assertEquals(2, todos.size());
    }
}
