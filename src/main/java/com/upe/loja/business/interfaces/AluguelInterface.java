package com.upe.loja.business.interfaces;

public interface AluguelInterface{
  String verificarDisponibilidade(Produto produto);
  BigDecimal calcularValor(Produto produto);
  boolean verificarInadimplencia(Cliente cliente);
}
