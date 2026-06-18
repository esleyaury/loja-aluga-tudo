package com.upe.loja.service.interfaces;
public interface MultaPorAtraso{
  // Valor cobrado extra em caso de atraso.
  // Tem que ter o numero de dias ultrapassados
  // uma taxa fixa, e um percentual sobre os dias.
  // Talvez tenha que criar uma variavel so pra 
  // taxa fixa e o percentual, porem eles sao 
  // por enquanto variaveis do metodo.
  float multaAtraso(int numDiasAtraso);
}
