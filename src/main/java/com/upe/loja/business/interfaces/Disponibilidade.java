package com.upe.loja.service.interfaces;

public interface Disponibilidade{
  // Precisa se uma funcao pra verificar 
  // se o item ta disponivel ou nao!
  // caso ele esteja em: "alugado" ou "em manutencao"
  // o item nao podera ser alugado.
  boolean verificarDisponibilidade(boolean status);
}
