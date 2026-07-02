package com.upe.loja.business.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.upe.loja.business.RelatorioBusiness.ItemAlugado;
import com.upe.loja.business.RelatorioBusiness.RelatorioFaturamento;
import com.upe.loja.repository.entity.Contrato;
import com.upe.loja.repository.entity.Produto;

public interface IRelatorioBusiness {
    Map<String, List<Produto>> itensDisponiveisPorCategoria();
    List<Contrato> historicoAlugueisPorCliente(String cpfCliente);
    List<ItemAlugado> itensAlugadosNoMomento();
    RelatorioFaturamento faturamentoNoPeriodo(LocalDate inicio, LocalDate fim);
}
