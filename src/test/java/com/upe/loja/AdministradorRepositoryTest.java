package com.upe.loja;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upe.loja.repository.AdministradorRepository;
import com.upe.loja.repository.entity.Administrador;

/**
 * Testes de unidade da camada de persistência (AdministradorRepository),
 * cobrindo operações de CRUD em memória e a correta persistência/recarga
 * a partir do arquivo CSV (administradores.csv).
 *
 * Cada teste recria o arquivo de persistência para não conflitar com dados
 * reais e garantir isolamento entre os casos de teste.
 */
public class AdministradorRepositoryTest {

    private static final String ARQUIVO_TESTE = "administradores.csv";
    private AdministradorRepository administradorRepository;

    @BeforeEach
    public void setUp() {
        new File(ARQUIVO_TESTE).delete();
        administradorRepository = new AdministradorRepository();
    }

    @AfterEach
    public void tearDown() {
        new File(ARQUIVO_TESTE).delete();
    }

    private Administrador criarAdministrador(String cpf, String nome) {
        return new Administrador(cpf, "senha123", nome, nome.toLowerCase().replace(" ", ".") + "@email.com");
    }

    @Test
    public void deveSalvarESerEncontradoPorCpf() {
        Administrador administrador = criarAdministrador("12345678901", "Maria Silva");

        administradorRepository.salvar(administrador);

        Administrador encontrado = administradorRepository.buscarPorCpf("12345678901");
        assertNotNull(encontrado);
    }

    @Test
    public void buscarPorCpfInexistenteDeveRetornarNull() {
        assertNull(administradorRepository.buscarPorCpf("00000000000"));
    }

    @Test
    public void deveListarTodosOsAdministradoresSalvos() {
        administradorRepository.salvar(criarAdministrador("12345678901", "Maria Silva"));
        administradorRepository.salvar(criarAdministrador("98765432100", "Joao Souza"));

        Map<String, Administrador> todos = administradorRepository.listarTodos();

        assertEquals(2, todos.size());
    }

    @Test
    public void listarTodosNaoDevePermitirAlterarEstadoInternoDoRepositorio() {
        administradorRepository.salvar(criarAdministrador("12345678901", "Maria Silva"));

        Map<String, Administrador> copia = administradorRepository.listarTodos();
        copia.clear(); // altera apenas a cópia retornada

        assertEquals(1, administradorRepository.listarTodos().size(), "o mapa interno do repositório não deve ser afetado");
    }

    @Test
    public void deveAtualizarDadosDeUmAdministradorJaSalvo() {
        Administrador administrador = criarAdministrador("12345678901", "Maria Silva");
        administradorRepository.salvar(administrador);

        administrador.setNome("Maria Oliveira");
        administradorRepository.atualizar(administrador);

        Administrador atualizado = administradorRepository.buscarPorCpf(administrador.getCpf());
        assertEquals("Maria Oliveira", atualizado.getNome());
    }

    @Test
    public void deveRemoverAdministradorDoRepositorio() {
        Administrador administrador = criarAdministrador("12345678901", "Maria Silva");
        administradorRepository.salvar(administrador);

        administradorRepository.remover(administrador.getCpf());

        assertNull(administradorRepository.buscarPorCpf(administrador.getCpf()));
    }

    @Test
    public void removerCpfInexistenteNaoDeveLancarErro() {
        administradorRepository.remover("cpf-que-nao-existe");
        // não deve lançar exceção; apenas confirma que a chamada é segura
        assertTrue(administradorRepository.listarTodos().isEmpty());
    }

    @Test
    public void deveGuardarDadosEPermitirRecarregarDoArquivoCsv() {
        Administrador administrador = criarAdministrador("12345678901", "Maria Silva");
        administradorRepository.salvar(administrador);

        administradorRepository.guardarDados();

        // Simula uma nova execução do programa: nova instância de repositório,
        // que deve carregar os dados persistidos no administradores.csv
        AdministradorRepository repositorioRecarregado = new AdministradorRepository();
        Administrador recarregado = repositorioRecarregado.buscarPorCpf("12345678901");

        assertNotNull(recarregado, "administrador deveria ter sido recarregado do CSV");
        assertEquals(administrador.getCpf(), recarregado.getCpf());
        assertEquals("Maria Silva", recarregado.getNome());
        assertEquals("maria.silva@email.com", recarregado.getEmail());
        assertEquals("senha123", recarregado.getSenha());
    }

    @Test
    public void deveCriarArquivoCsvVazioQuandoNaoExiste() {
        File arquivo = new File(ARQUIVO_TESTE);
        assertTrue(arquivo.exists(), "o arquivo administradores.csv deve ser criado automaticamente na primeira execução");
    }

    @Test
    public void deveManterMultiplosAdministradoresAoSalvarEGuardarDados() {
        Administrador a1 = criarAdministrador("12345678901", "Maria Silva");
        Administrador a2 = criarAdministrador("98765432100", "Joao Souza");
        administradorRepository.salvar(a1);
        administradorRepository.salvar(a2);
        administradorRepository.guardarDados();

        AdministradorRepository repositorioRecarregado = new AdministradorRepository();
        List<Administrador> todos = List.copyOf(repositorioRecarregado.listarTodos().values());

        assertEquals(2, todos.size());
    }
}
