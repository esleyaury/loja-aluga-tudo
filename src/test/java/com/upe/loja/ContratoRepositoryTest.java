package com.upe.loja;

import com.upe.loja.repository.ContratoRepository;
import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Contrato.StatusContrato;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ContratoRepositoryTest {

    private ContratoRepository repository;
    private final File arquivoContratos = new File("contratos.csv");

    @BeforeEach
    void setup() {
        repository = new ContratoRepository();
    }

    @AfterEach
    void limpar() {
        arquivoContratos.delete();
    }

    private Contrato criarContrato(String cpfCliente) {
        return new Contrato(
                cpfCliente,
                "111.111.111-11",
                Set.of("prod1", "prod2"),
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                new BigDecimal("150.00")
        );
    }

    @Test
    void deveSalvarContrato() {
        Contrato contrato = criarContrato("123.456.789-00");
        repository.salvar(contrato);

        assertNotNull(repository.buscarPorId(contrato.getId()));
    }

    @Test
    void deveBuscarContratoPorId() {
        Contrato contrato = criarContrato("123.456.789-00");
        repository.salvar(contrato);

        Contrato encontrado = repository.buscarPorId(contrato.getId());

        assertEquals(contrato.getId(), encontrado.getId());
        assertEquals("123.456.789-00", encontrado.getCpfCliente());
    }

    @Test
    void deveRetornarNullQuandoIdNaoExiste() {
        assertNull(repository.buscarPorId(99999L));
    }

    @Test
    void deveListarTodosOsContratos() {
        repository.salvar(criarContrato("111.111.111-11"));
        repository.salvar(criarContrato("222.222.222-22"));

        Map<Long, Contrato> todos = repository.listarTodos();

        assertEquals(2, todos.size());
    }

    @Test
    void listarTodosDeveRetornarCopiaIndependente() {
        Contrato contrato = criarContrato("123.456.789-00");
        repository.salvar(contrato);

        Map<Long, Contrato> copia = repository.listarTodos();
        copia.clear();

        assertEquals(1, repository.listarTodos().size());
    }

    @Test
    void deveBuscarContratosPorCpfCliente() {
        Contrato c1 = criarContrato("123.456.789-00");
        Contrato c2 = criarContrato("123.456.789-00");
        Contrato c3 = criarContrato("999.999.999-99");

        repository.salvar(c1);
        repository.salvar(c2);
        repository.salvar(c3);

        List<Contrato> resultado = repository.buscarPorCpfCliente("123.456.789-00");

        assertEquals(2, resultado.size());
    }

    @Test
    void buscarPorCpfDeveRetornarVazioQuandoNaoExistir() {
        List<Contrato> resultado = repository.buscarPorCpfCliente("000.000.000-00");

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveBuscarContratosAtivos() {
        Contrato ativo    = criarContrato("123.456.789-00");
        Contrato encerrado = criarContrato("999.999.999-99");
        encerrado.encerrar();

        repository.salvar(ativo);
        repository.salvar(encerrado);

        List<Contrato> ativos = repository.buscarAtivos();

        assertEquals(1, ativos.size());
        assertEquals(StatusContrato.ATIVO, ativos.get(0).getStatus());
    }

    @Test
    void deveAtualizarContrato() {
        Contrato contrato = criarContrato("123.456.789-00");
        repository.salvar(contrato);

        contrato.encerrar();
        repository.atualizar(contrato);

        assertEquals(StatusContrato.ENCERRADO, repository.buscarPorId(contrato.getId()).getStatus());
    }

    @Test
    void deveGuardarDadosNoArquivo() {
        Contrato contrato = criarContrato("123.456.789-00");
        repository.salvar(contrato);
        repository.guardarDados();

        assertTrue(arquivoContratos.exists());

        ContratoRepository novoRepository = new ContratoRepository();
        assertNotNull(novoRepository.buscarPorId(contrato.getId()));
    }
}