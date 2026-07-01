package com.upe.loja.business;

import com.upe.loja.business.interfaces.IContratoBusiness;
import com.upe.loja.repository.ContratoRepository;
import com.upe.loja.repository.OcorrenciaRepository;
import com.upe.loja.repository.ProdutoRepository;
import com.upe.loja.repository.interfaces.IContratoRepository;
import com.upe.loja.repository.interfaces.IOcorrenciaRepository;
import com.upe.loja.repository.interfaces.IProdutoRepository;
import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Ocorrencia;
import com.upe.loja.repository.entity.Produto;
import com.upe.loja.repository.entity.Produto.EstadoProduto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ContratoBusiness implements IContratoBusiness {

    private final IContratoRepository contratos;
    private final IProdutoRepository produtos;
    private final IOcorrenciaRepository ocorrencias;

    public ContratoBusiness(ContratoRepository contratos, ProdutoRepository produtos,
                            OcorrenciaRepository ocorrencias) {
        this.contratos  = contratos;
        this.produtos   = produtos;
        this.ocorrencias = ocorrencias;
    }

    @Override
    public Contrato criarContrato(String cpfCliente, String cpfFuncionario,
                                  List<String> idsProdutos, LocalDate dataRetirada,
                                  LocalDate dataDevolucaoPrevista) {

        if (cpfCliente == null || cpfCliente.isBlank())
            throw new IllegalArgumentException("CPF do cliente é obrigatório.");
        if (idsProdutos == null || idsProdutos.isEmpty())
            throw new IllegalArgumentException("O contrato deve ter ao menos um produto.");

        long diasAluguel = ChronoUnit.DAYS.between(dataRetirada, dataDevolucaoPrevista);
        if (diasAluguel <= 0)
            throw new IllegalArgumentException("Data de devolução deve ser após a retirada.");

        // RN01 - Disponibilidade: verifica se todos os produtos estão disponíveis
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (String idProduto : idsProdutos) {
            Produto produto = produtos.buscarPorId(idProduto);
            if (produto == null)
                throw new IllegalArgumentException("Produto não encontrado: " + idProduto);
            if (produto.getEstado() != EstadoProduto.DISPONIVEL)
                throw new IllegalArgumentException("Produto indisponível para aluguel: " + produto.getNome());
            valorTotal = valorTotal.add(produto.getTaxaDiaria().multiply(new BigDecimal(diasAluguel)));
        }

        // RN04 - Inadimplência: verifica se cliente tem multas pendentes
        for (Contrato c : contratos.buscarPorCpfCliente(cpfCliente)) {
            Ocorrencia ocorrencia = ocorrencias.buscarPorContrato(c.getId());
            if (ocorrencia != null && !ocorrencia.isQuitada())
                throw new IllegalArgumentException("Cliente possui multas pendentes e não pode realizar novos aluguéis.");
        }

        // Marca todos os produtos como ALUGADO
        for (String idProduto : idsProdutos) {
            Produto produto = produtos.buscarPorId(idProduto);
            produto.setEstado(EstadoProduto.ALUGADO);
            produtos.atualizar(produto);
        }

        Contrato contrato = new Contrato(cpfCliente, cpfFuncionario,
                new HashSet<>(idsProdutos), dataRetirada, dataDevolucaoPrevista, valorTotal);

        contratos.salvar(contrato);
        return contrato;
    }

    @Override
    public Contrato buscarPorId(long id) {
        return contratos.buscarPorId(id);
    }

    @Override
    public List<Contrato> buscarPorCpfCliente(String cpfCliente) {
        return contratos.buscarPorCpfCliente(cpfCliente);
    }

    @Override
    public List<Contrato> buscarAtivos() {
        return contratos.buscarAtivos();
    }

    @Override
    public Map<Long, Contrato> listarTodos() {
        return contratos.listarTodos();
    }

    @Override
    public void encerrarContrato(long id) {
        Contrato contrato = contratos.buscarPorId(id);
        if (contrato == null)
            throw new IllegalArgumentException("Contrato não encontrado.");
        if (contrato.getStatus() == Contrato.StatusContrato.ENCERRADO)
            throw new IllegalArgumentException("Contrato já está encerrado.");

        // Verifica se há ocorrência/multa pendente
        Ocorrencia ocorrencia = ocorrencias.buscarPorContrato(id);
        if (ocorrencia != null && !ocorrencia.isQuitada())
            throw new IllegalArgumentException("Contrato possui multa pendente. Quite a multa antes de encerrar.");

        // Sem pendências: volta produtos para DISPONIVEL
        for (String idProduto : contrato.getIdsProdutos()) {
            Produto produto = produtos.buscarPorId(idProduto);
            if (produto != null) {
                produto.setEstado(EstadoProduto.DISPONIVEL);
                produtos.atualizar(produto);
            }
        }

        contrato.encerrar();
        contratos.atualizar(contrato);
    }

    @Override
    public void guardarDados() {
        contratos.guardarDados();
    }
}