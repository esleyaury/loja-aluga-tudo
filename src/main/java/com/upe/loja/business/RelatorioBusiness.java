package com.upe.loja.business;

import com.upe.loja.business.interfaces.IRelatorioBusiness;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioBusiness implements IRelatorioBusiness {

    private final IProdutoRepository produtos;
    private final IContratoRepository contratos;
    private final IOcorrenciaRepository ocorrencias;

    public RelatorioBusiness(ProdutoRepository produtos, ContratoRepository contratos,
                             OcorrenciaRepository ocorrencias) {
        this.produtos    = produtos;
        this.contratos   = contratos;
        this.ocorrencias = ocorrencias;
    }

    // Contrato somado a um indicador de atraso, usado no relatório de itens alugados no momento.
    public record ItemAlugado(Contrato contrato, boolean emAtraso) {}

    // Composição do faturamento em um período: aluguéis, multas e o total geral.
    public record RelatorioFaturamento(BigDecimal totalAlugueis, BigDecimal totalMultas, BigDecimal totalGeral) {}

    @Override
    public Map<String, List<Produto>> itensDisponiveisPorCategoria() {
        return produtos.listarTodos().values().stream()
                .filter(p -> p.getEstado() == EstadoProduto.DISPONIVEL)
                .collect(Collectors.groupingBy(Produto::getCategoria));
    }

    @Override
    public List<Contrato> historicoAlugueisPorCliente(String cpfCliente) {
        return contratos.buscarPorCpfCliente(cpfCliente).stream()
                .sorted(Comparator.comparing(Contrato::getDataRetirada))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemAlugado> itensAlugadosNoMomento() {
        LocalDate hoje = LocalDate.now();
        return contratos.buscarAtivos().stream()
                .map(c -> new ItemAlugado(c, hoje.isAfter(c.getDataDevolucaoPrevista())))
                .collect(Collectors.toList());
    }

    @Override
    public RelatorioFaturamento faturamentoNoPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null || fim.isBefore(inicio)) {
            throw new IllegalArgumentException("Período inválido: a data final deve ser após a inicial.");
        }

        BigDecimal totalAlugueis = contratos.listarTodos().values().stream()
                .filter(c -> !c.getDataRetirada().isBefore(inicio) && !c.getDataRetirada().isAfter(fim))
                .map(Contrato::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalMultas = ocorrencias.listarTodas().values().stream()
                .filter(o -> !o.getDataOcorrencia().isBefore(inicio) && !o.getDataOcorrencia().isAfter(fim))
                .map(Ocorrencia::getValorMulta)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RelatorioFaturamento(totalAlugueis, totalMultas, totalAlugueis.add(totalMultas));
    }
}
