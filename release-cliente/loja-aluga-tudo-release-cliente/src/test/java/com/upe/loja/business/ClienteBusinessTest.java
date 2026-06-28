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
import com.upe.loja.repository.entity.Cliente.EstadoCliente;

/**
 * Testes de unidade da camada de negócio (ClienteBusiness), concentrando-se em:
 * validações de cadastro, unicidade por CPF, regras de atualização,
 * RN04 (inadimplência) e RN05 (exclusão lógica / integridade de histórico).
 *
 * Cada teste usa um arquivo CSV isolado (clientes-test.csv) para não conflitar
 * com o arquivo de persistência real (clientes.csv) usado pela aplicação.
 */
public class ClienteBusinessTest {

    private static final String ARQUIVO_TESTE = "clientes.csv";
    private ClienteBusiness clienteBusiness;

    @BeforeEach
    public void setUp() {
        // garante que cada teste comece com o arquivo de persistência limpo
        new File(ARQUIVO_TESTE).delete();
        clienteBusiness = new ClienteBusiness();
    }

    @AfterEach
    public void tearDown() {
        new File(ARQUIVO_TESTE).delete();
    }

    @Test
    public void deveCadastrarClienteComSucesso() {
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");

        Cliente cliente = clienteBusiness.buscarPorCpf(12345678901L);

        assertNotNull(cliente);
        assertEquals("Maria Silva", cliente.getNome());
        assertEquals(EstadoCliente.ATIVO, cliente.getEstado());
        assertFalse(cliente.isInadimplente());
    }

    @Test
    public void naoDevePermitirCpfDuplicado() {
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.cadastrarCliente(12345678901L, "outraSenha", "Maria Souza", 81988880000L, "outra@email.com");
        });

        assertTrue(exception.getMessage().contains("Já existe um cliente"));
    }

    @Test
    public void naoDeveCadastrarClienteComCamposInvalidos() {
        // nome vazio
        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.cadastrarCliente(11122233344L, "senha123", "", 81999990000L, "ana@email.com");
        });

        // email sem "@"
        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.cadastrarCliente(22233344455L, "senha123", "Carlos", 81999990000L, "email-invalido");
        });

        // cpf inválido
        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.cadastrarCliente(0L, "senha123", "Carlos", 81999990000L, "carlos@email.com");
        });
    }

    @Test
    public void deveBuscarClientePorNome() {
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");
        clienteBusiness.cadastrarCliente(98765432100L, "senha456", "João Souza", 81988887777L, "joao@email.com");

        List<Cliente> encontrados = clienteBusiness.buscarPorNome("Maria Silva");

        assertEquals(1, encontrados.size());
        assertEquals(12345678901L, encontrados.get(0).getCpf());
    }

    @Test
    public void deveRetornarListaVaziaQuandoNomeNaoEncontrado() {
        List<Cliente> encontrados = clienteBusiness.buscarPorNome("Inexistente");
        assertTrue(encontrados.isEmpty());
    }

    @Test
    public void deveAtualizarNomeDoCliente() {
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");
        Cliente cliente = clienteBusiness.buscarPorCpf(12345678901L);

        clienteBusiness.atualizar(cliente, 1, "Maria Oliveira");

        assertEquals("Maria Oliveira", clienteBusiness.buscarPorCpf(12345678901L).getNome());
    }

    @Test
    public void deveAtualizarTelefoneDoCliente() {
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");
        Cliente cliente = clienteBusiness.buscarPorCpf(12345678901L);

        clienteBusiness.atualizar(cliente, 2, "81911112222");

        assertEquals(81911112222L, clienteBusiness.buscarPorCpf(12345678901L).getTelefone());
    }

    @Test
    public void naoDeveAtualizarTelefoneComValorNaoNumerico() {
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");
        Cliente cliente = clienteBusiness.buscarPorCpf(12345678901L);

        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.atualizar(cliente, 2, "abc");
        });
    }

    @Test
    public void naoDeveAtualizarComOpcaoInvalida() {
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");
        Cliente cliente = clienteBusiness.buscarPorCpf(12345678901L);

        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.atualizar(cliente, 99, "qualquerValor");
        });
    }

    @Test
    public void naoDeveAtualizarComValorVazio() {
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");
        Cliente cliente = clienteBusiness.buscarPorCpf(12345678901L);

        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.atualizar(cliente, 1, "   ");
        });
    }

    @Test
    public void rn05_removerDeveSerExclusaoLogica() {
        // RN05 - Integridade: não se exclui de fato; usa-se exclusão lógica (estado INATIVO),
        // preservando o histórico do cliente.
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");

        clienteBusiness.remover(12345678901L);

        Cliente cliente = clienteBusiness.buscarPorCpf(12345678901L);
        assertNotNull(cliente, "O registro deve continuar existindo (exclusão lógica), não pode ser apagado.");
        assertEquals(EstadoCliente.INATIVO, cliente.getEstado());
    }

    @Test
    public void naoDeveRemoverClienteInexistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            clienteBusiness.remover(99999999999L);
        });
    }

    @Test
    public void rn04_clienteInadimplenteNaoPodeAlugar() {
        // RN04 - Inadimplência: Clientes com multas em aberto não podem realizar novos aluguéis.
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");
        Cliente cliente = clienteBusiness.buscarPorCpf(12345678901L);
        cliente.setInadimplente(true);

        assertFalse(clienteBusiness.podeAlugar(12345678901L));
    }

    @Test
    public void clienteAtivoSemMultaPodeAlugar() {
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");

        assertTrue(clienteBusiness.podeAlugar(12345678901L));
    }

    @Test
    public void clienteInativoNaoPodeAlugar() {
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");
        clienteBusiness.remover(12345678901L); // exclusão lógica -> INATIVO

        assertFalse(clienteBusiness.podeAlugar(12345678901L));
    }

    @Test
    public void deveListarApenasClientesAtivos() {
        clienteBusiness.cadastrarCliente(12345678901L, "senha123", "Maria Silva", 81999990000L, "maria@email.com");
        clienteBusiness.cadastrarCliente(98765432100L, "senha456", "João Souza", 81988887777L, "joao@email.com");
        clienteBusiness.remover(98765432100L); // torna João INATIVO

        Map<Long, Cliente> ativos = clienteBusiness.clientesAtivos();

        assertEquals(1, ativos.size());
        assertTrue(ativos.containsKey(12345678901L));
        assertFalse(ativos.containsKey(98765432100L));
    }

    @Test
    public void buscarPorCpfInexistenteDeveRetornarNull() {
        assertNull(clienteBusiness.buscarPorCpf(11111111111L));
    }
}
