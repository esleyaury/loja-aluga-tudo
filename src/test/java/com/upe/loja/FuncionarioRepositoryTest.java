package com.upe.loja;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upe.loja.repository.FuncionarioRepository;
import com.upe.loja.repository.entity.Funcionario;
import com.upe.loja.repository.entity.Funcionario.Cargo;

/**
 * Testes de unidade da camada de persistência (FuncionarioRepository),
 * cobrindo operações de CRUD em memória e a correta persistência/recarga
 * a partir do arquivo CSV (funcionarios.csv).
 *
 * Cada teste recria o arquivo de persistência para não conflitar com dados
 * reais e garantir isolamento entre os casos de teste.
 */
public class FuncionarioRepositoryTest {

    private static final String ARQUIVO_TESTE = "funcionarios.csv";
    private FuncionarioRepository funcionarioRepository;

    @BeforeEach
    public void setUp() {
        new File(ARQUIVO_TESTE).delete();
        funcionarioRepository = new FuncionarioRepository();
    }

    @AfterEach
    public void tearDown() {
        new File(ARQUIVO_TESTE).delete();
    }

    private Funcionario criarFuncionario(String cpf, String nome) {
        return new Funcionario(cpf, "senha123", nome, nome.toLowerCase().replace(" ", ".") + "@email.com",
                new BigDecimal("2500.00"), Cargo.ATENDENTE);
    }

    @Test
    public void deveSalvarESerEncontradoPorCpf() {
        Funcionario funcionario = criarFuncionario("12345678901", "Maria Silva");

        funcionarioRepository.salvar(funcionario);

        Funcionario encontrado = funcionarioRepository.buscarPorCpf("12345678901");
        assertNotNull(encontrado);
    }

    @Test
    public void buscarPorCpfInexistenteDeveRetornarNull() {
        assertNull(funcionarioRepository.buscarPorCpf("00000000000"));
    }

    @Test
    public void deveListarTodosOsFuncionariosSalvos() {
        funcionarioRepository.salvar(criarFuncionario("12345678901", "Maria Silva"));
        funcionarioRepository.salvar(criarFuncionario("98765432100", "Joao Souza"));

        Map<String, Funcionario> todos = funcionarioRepository.listarTodos();

        assertEquals(2, todos.size());
    }

    @Test
    public void listarTodosNaoDevePermitirAlterarEstadoInternoDoRepositorio() {
        funcionarioRepository.salvar(criarFuncionario("12345678901", "Maria Silva"));

        Map<String, Funcionario> copia = funcionarioRepository.listarTodos();
        copia.clear(); // altera apenas a cópia retornada

        assertEquals(1, funcionarioRepository.listarTodos().size(), "o mapa interno do repositório não deve ser afetado");
    }

    @Test
    public void deveAtualizarDadosDeUmFuncionarioJaSalvo() {
        Funcionario funcionario = criarFuncionario("12345678901", "Maria Silva");
        funcionarioRepository.salvar(funcionario);

        funcionario.setNome("Maria Oliveira");
        funcionarioRepository.atualizar(funcionario);

        Funcionario atualizado = funcionarioRepository.buscarPorCpf(funcionario.getCpf());
        assertEquals("Maria Oliveira", atualizado.getNome());
    }

    @Test
    public void deveRemoverFuncionarioDoRepositorio() {
        Funcionario funcionario = criarFuncionario("12345678901", "Maria Silva");
        funcionarioRepository.salvar(funcionario);

        funcionarioRepository.remover(funcionario.getCpf());

        assertNull(funcionarioRepository.buscarPorCpf(funcionario.getCpf()));
    }

    @Test
    public void removerCpfInexistenteNaoDeveLancarErro() {
        funcionarioRepository.remover("cpf-que-nao-existe");
        // não deve lançar exceção; apenas confirma que a chamada é segura
        assertTrue(funcionarioRepository.listarTodos().isEmpty());
    }

    @Test
    public void deveGuardarDadosEPermitirRecarregarDoArquivoCsv() {
        Funcionario funcionario = criarFuncionario("12345678901", "Maria Silva");
        funcionarioRepository.salvar(funcionario);

        funcionarioRepository.guardarDados();

        // Simula uma nova execução do programa: nova instância de repositório,
        // que deve carregar os dados persistidos no funcionarios.csv
        FuncionarioRepository repositorioRecarregado = new FuncionarioRepository();
        Funcionario recarregado = repositorioRecarregado.buscarPorCpf("12345678901");

        assertNotNull(recarregado, "funcionário deveria ter sido recarregado do CSV");
        assertEquals(funcionario.getCpf(), recarregado.getCpf());
        assertEquals("Maria Silva", recarregado.getNome());
        assertEquals("maria.silva@email.com", recarregado.getEmail());
        assertEquals(0, new BigDecimal("2500.00").compareTo(recarregado.getSalario()));
        assertEquals(Cargo.ATENDENTE, recarregado.getCargo());
    }

    @Test
    public void deveCriarArquivoCsvVazioQuandoNaoExiste() {
        File arquivo = new File(ARQUIVO_TESTE);
        assertTrue(arquivo.exists(), "o arquivo funcionarios.csv deve ser criado automaticamente na primeira execução");
    }

    @Test
    public void deveManterMultiplosFuncionariosAoSalvarEGuardarDados() {
        Funcionario f1 = criarFuncionario("12345678901", "Maria Silva");
        Funcionario f2 = criarFuncionario("98765432100", "Joao Souza");
        funcionarioRepository.salvar(f1);
        funcionarioRepository.salvar(f2);
        funcionarioRepository.guardarDados();

        FuncionarioRepository repositorioRecarregado = new FuncionarioRepository();
        List<Funcionario> todos = List.copyOf(repositorioRecarregado.listarTodos().values());

        assertEquals(2, todos.size());
    }
}
