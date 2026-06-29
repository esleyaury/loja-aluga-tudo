package com.upe.loja.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upe.loja.repository.entity.Cliente;

/**
 * Testes de unidade da camada de negócio (ClienteBusiness): validações de
 * cadastro, unicidade por CPF, regras de atualização, RN04 (inadimplência)
 * e RN05 (exclusão lógica / integridade de histórico).
 *
 * Usa um arquivo CSV isolado (clientes.csv, recriado a cada teste) para não
 * conflitar com dados reais.
 */
public class ClienteBusinessTest {

    private static final String ARQUIVO_TESTE = "clientes.csv";
    private ClienteBusiness clienteBusiness;

    @BeforeEach
    public void setUp() {
        new File(ARQUIVO_TESTE).delete();
        clienteBusiness = new ClienteBusiness();
    }

    @AfterEach
    public void tearDown() {
        new File(ARQUIVO_TESTE).delete();
    }

    @Test
    public void deveCadastrarClienteComSucesso() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");

        Cliente cliente = clienteBusiness.buscarPorCpf("12345678901");

        assertNotNull(cliente);
        assertNotNull(cliente.getId(), "o id deve ser gerado automaticamente");
        assertEquals("Maria Silva", cliente.getNome());
        assertTrue(cliente.isAtivo());
        assertFalse(cliente.isInadimplente());
    }

    @Test
    public void naoDevePermitirCpfDuplicado() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.cadastrarCliente("12345678901", "outraSenha", "Maria Souza", "outra@email.com");
        });

        assertTrue(exception.getMessage().contains("Já existe um cliente"));
    }

    @Test
    public void naoDeveCadastrarClienteComCamposInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.cadastrarCliente("11122233344", "senha123", "", "ana@email.com");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.cadastrarCliente("22233344455", "senha123", "Carlos", "email-invalido");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.cadastrarCliente("", "senha123", "Carlos", "carlos@email.com");
        });
    }

    @Test
    public void deveBuscarClientePorNome() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        clienteBusiness.cadastrarCliente("98765432100", "senha456", "João Souza", "joao@email.com");

        List<Cliente> encontrados = clienteBusiness.buscarPorNome("Maria Silva");

        assertEquals(1, encontrados.size());
        assertEquals("12345678901", encontrados.get(0).getCpf());
    }

    @Test
    public void deveRetornarListaVaziaQuandoNomeNaoEncontrado() {
        List<Cliente> encontrados = clienteBusiness.buscarPorNome("Inexistente");
        assertTrue(encontrados.isEmpty());
    }

    @Test
    public void deveBuscarClientePorId() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        String id = clienteBusiness.buscarPorCpf("12345678901").getId();

        Cliente cliente = clienteBusiness.buscarPorId(id);

        assertNotNull(cliente);
        assertEquals("Maria Silva", cliente.getNome());
    }

    @Test
    public void deveAtualizarNomeDoCliente() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        Cliente cliente = clienteBusiness.buscarPorCpf("12345678901");

        clienteBusiness.atualizar(cliente, 1, "Maria Oliveira");

        assertEquals("Maria Oliveira", clienteBusiness.buscarPorCpf("12345678901").getNome());
    }

    @Test
    public void deveAtualizarEmailDoCliente() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        Cliente cliente = clienteBusiness.buscarPorCpf("12345678901");

        clienteBusiness.atualizar(cliente, 2, "novoemail@email.com");

        assertEquals("novoemail@email.com", clienteBusiness.buscarPorCpf("12345678901").getEmail());
    }

    @Test
    public void naoDeveAtualizarEmailComFormatoInvalido() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        Cliente cliente = clienteBusiness.buscarPorCpf("12345678901");

        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.atualizar(cliente, 2, "email-sem-arroba");
        });
    }

    @Test
    public void naoDeveAtualizarComOpcaoInvalida() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        Cliente cliente = clienteBusiness.buscarPorCpf("12345678901");

        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.atualizar(cliente, 99, "qualquerValor");
        });
    }

    @Test
    public void rn05_removerDeveSerExclusaoLogica() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        String id = clienteBusiness.buscarPorCpf("12345678901").getId();

        clienteBusiness.remover(id);

        Cliente cliente = clienteBusiness.buscarPorCpf("12345678901");
        assertNotNull(cliente, "O registro deve continuar existindo (exclusão lógica), não pode ser apagado.");
        assertFalse(cliente.isAtivo());
    }

    @Test
    public void naoDeveRemoverClienteInexistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.remover("id-que-nao-existe");
        });
    }

    @Test
    public void rn04_clienteInadimplenteNaoPodeAlugar() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        Cliente cliente = clienteBusiness.buscarPorCpf("12345678901");
        cliente.setInadimplente(true);

        assertFalse(clienteBusiness.podeAlugar("12345678901"));
    }

    @Test
    public void clienteAtivoSemMultaPodeAlugar() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");

        assertTrue(clienteBusiness.podeAlugar("12345678901"));
    }

    @Test
    public void clienteInativoNaoPodeAlugar() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        String id = clienteBusiness.buscarPorCpf("12345678901").getId();
        clienteBusiness.remover(id);

        assertFalse(clienteBusiness.podeAlugar("12345678901"));
    }

    @Test
    public void deveListarApenasClientesAtivos() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        clienteBusiness.cadastrarCliente("98765432100", "senha456", "João Souza", "joao@email.com");
        String idJoao = clienteBusiness.buscarPorCpf("98765432100").getId();
        clienteBusiness.remover(idJoao);

        Map<String, Cliente> ativos = clienteBusiness.clientesAtivos();

        assertEquals(1, ativos.size());
        assertTrue(ativos.values().stream().anyMatch(c -> c.getCpf().equals("12345678901")));
    }

    @Test
    public void buscarPorCpfInexistenteDeveRetornarNull() {
        assertNull(clienteBusiness.buscarPorCpf("11111111111"));
    }

    @Test
    public void persistenciaCSV_recarregaDadosCorretamente() {
        clienteBusiness.cadastrarCliente("12345678901", "senha123", "Maria Silva", "maria@email.com");
        String idOriginal = clienteBusiness.buscarPorCpf("12345678901").getId();
        clienteBusiness.guardarDados();

        // Simula uma nova execução do programa: nova instância, deve carregar do CSV
        ClienteBusiness recarregado = new ClienteBusiness();
        Cliente cliente = recarregado.buscarPorCpf("12345678901");

        assertNotNull(cliente, "cliente deveria ter sido recarregado do CSV");
        assertEquals(idOriginal, cliente.getId(), "o id deve ser preservado entre execuções");
        assertEquals("Maria Silva", cliente.getNome());
        assertEquals("maria@email.com", cliente.getEmail());
        assertTrue(cliente.isAtivo());
    }
}
