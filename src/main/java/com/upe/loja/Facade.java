package com.upe.loja;

import com.upe.loja.business.ContratoBusiness;
import com.upe.loja.business.OcorrenciaBusiness;
import com.upe.loja.business.interfaces.IContratoBusiness;
import com.upe.loja.business.interfaces.IOcorrenciaBusiness;
import com.upe.loja.repository.ContratoRepository;
import com.upe.loja.repository.OcorrenciaRepository;
import com.upe.loja.repository.ProdutoRepository;
import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Ocorrencia;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Facade {

    private final IContratoBusiness contratoBusiness;
    private final IOcorrenciaBusiness ocorrenciaBusiness;

    public Facade() {
        ContratoRepository contratoRepository    = new ContratoRepository();
        OcorrenciaRepository ocorrenciaRepository = new OcorrenciaRepository();
        ProdutoRepository produtoRepository       = new ProdutoRepository();

        this.contratoBusiness  = new ContratoBusiness(contratoRepository, produtoRepository, ocorrenciaRepository);
        this.ocorrenciaBusiness = new OcorrenciaBusiness(ocorrenciaRepository, contratoRepository);
    }

    // Contrato
    public Contrato criarContrato(String cpfCliente, String cpfFuncionario,
            List<String> idsProdutos, LocalDate dataRetirada, LocalDate dataDevolucaoPrevista) {
        return contratoBusiness.criarContrato(cpfCliente, cpfFuncionario,
                idsProdutos, dataRetirada, dataDevolucaoPrevista);
    }

    public Contrato buscarContratoPorId(long id) {
        return contratoBusiness.buscarPorId(id);
    }

    public List<Contrato> buscarContratosPorCliente(String cpfCliente) {
        return contratoBusiness.buscarPorCpfCliente(cpfCliente);
    }

    public List<Contrato> buscarContratosAtivos() {
        return contratoBusiness.buscarAtivos();
    }

    public Map<Long, Contrato> listarContratos() {
        return contratoBusiness.listarTodos();
    }

    public void encerrarContrato(long id) {
        contratoBusiness.encerrarContrato(id);
    }

    // Ocorrencia
    public void registrarAtraso(long idContrato, LocalDate dataDevolucaoReal,
            long diasAtraso, BigDecimal valorDiaria) {
        ocorrenciaBusiness.registrarAtraso(idContrato, dataDevolucaoReal, diasAtraso, valorDiaria);
    }

    public void registrarAvaria(long idContrato, String descricao) {
        ocorrenciaBusiness.registrarAvaria(idContrato, descricao);
    }

    public void quitarOcorrencia(long idContrato) {
        ocorrenciaBusiness.quitar(idContrato);
    }

    public Ocorrencia buscarOcorrenciaPorContrato(long idContrato) {
        return ocorrenciaBusiness.buscarPorContrato(idContrato);
    }

    public boolean clienteTemPendencias(String cpfCliente) {
        return ocorrenciaBusiness.clienteTemPendencias(cpfCliente);
    }

    public Map<Long, Ocorrencia> listarOcorrencias() {
        return ocorrenciaBusiness.listarTodas();
    }

    public void removerOcorrencia(long idContrato) {
        ocorrenciaBusiness.remover(idContrato);
    }

    // Persistência
    public void fecharPrograma() {
        contratoBusiness.guardarDados();
        ocorrenciaBusiness.guardarDados();
    }
}