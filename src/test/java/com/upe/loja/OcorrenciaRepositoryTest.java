package com.upe.loja;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.upe.loja.repository.OcorrenciaRepository;
import com.upe.loja.repository.entity.Ocorrencia;
import com.upe.loja.repository.entity.Ocorrencia.TipoOcorrencia;

class OcorrenciaRepositoryTest {

    private OcorrenciaRepository repository;
    private final File arquivoOcorrencias = new File("ocorrencias.csv");

    @BeforeEach
    void setup() {
        repository = new OcorrenciaRepository();
    }

    @AfterEach
    void limpar() {
        arquivoOcorrencias.delete();
    }

    private Ocorrencia criarAtraso(long idContrato) {
        return new Ocorrencia(idContrato, TipoOcorrencia.ATRASO,
                LocalDate.now(), "Devolução com 3 dias de atraso.", new BigDecimal("65.00"));
    }

    private Ocorrencia criarAvaria(long idContrato) {
        return new Ocorrencia(idContrato, TipoOcorrencia.AVARIA,
                LocalDate.now(), "Arranhão na superfície.", new BigDecimal("100.00"));
    }

    @Test
    void deveSalvarOcorrencia() {
        repository.salvar(criarAtraso(1L));

        assertNotNull(repository.buscarPorContrato(1L));
    }

    @Test
    void deveBuscarOcorrenciaPorContrato() {
        Ocorrencia ocorrencia = criarAtraso(1L);
        repository.salvar(ocorrencia);

        Ocorrencia encontrada = repository.buscarPorContrato(1L);

        assertEquals(TipoOcorrencia.ATRASO, encontrada.getTipo());
        assertEquals(1L, encontrada.getIdContrato());
    }

    @Test
    void deveRetornarNullQuandoContratoNaoTemOcorrencia() {
        assertNull(repository.buscarPorContrato(99L));
    }

    @Test
    void deveListarTodasAsOcorrencias() {
        repository.salvar(criarAtraso(1L));
        repository.salvar(criarAvaria(2L));

        Map<Long, Ocorrencia> todas = repository.listarTodas();

        assertEquals(2, todas.size());
    }

    @Test
    void listarTodasDeveRetornarCopiaIndependente() {
        repository.salvar(criarAtraso(1L));

        Map<Long, Ocorrencia> copia = repository.listarTodas();
        copia.clear();

        assertEquals(1, repository.listarTodas().size());
    }

    @Test
    void deveBuscarOcorrenciasNaoQuitadas() {
        Ocorrencia atraso = criarAtraso(1L);
        Ocorrencia avaria = criarAvaria(2L);
        avaria.quitar();

        repository.salvar(atraso);
        repository.salvar(avaria);

        List<Ocorrencia> naoQuitadas = repository.buscarNaoQuitadas();

        assertEquals(1, naoQuitadas.size());
        assertFalse(naoQuitadas.get(0).isQuitada());
    }

    @Test
    void deveAtualizarOcorrencia() {
        Ocorrencia ocorrencia = criarAtraso(1L);
        repository.salvar(ocorrencia);

        ocorrencia.quitar();
        repository.atualizar(ocorrencia);

        assertTrue(repository.buscarPorContrato(1L).isQuitada());
    }

    @Test
    void deveRemoverOcorrencia() {
        repository.salvar(criarAtraso(1L));

        repository.remover(1L);

        assertNull(repository.buscarPorContrato(1L));
    }

    @Test
    void removerIdInexistenteNaoDeveLancarErro() {
        assertDoesNotThrow(() -> repository.remover(99999L));
    }

    @Test
    void deveGuardarDadosNoArquivo() {
        repository.salvar(criarAtraso(1L));
        repository.guardarDados();

        assertTrue(arquivoOcorrencias.exists());

        OcorrenciaRepository novoRepository = new OcorrenciaRepository();
        assertNotNull(novoRepository.buscarPorContrato(1L));
    }
}