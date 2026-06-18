package com.upe.loja.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ProdutoRepository {

    private ObjectMapper mapper;
    private Produto produto;

    String jsonSrString = mapper.writeValueAsString((produto));
}
