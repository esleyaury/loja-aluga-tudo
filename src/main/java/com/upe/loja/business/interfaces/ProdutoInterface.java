package com.upe.loja.business.interfaces;
import java.util.List;

import com.upe.loja.repository.ProdutoRepository;
import com.upe.loja.repository.entity.Produto;

public interface ProdutoInterface {

    public List<Produto> verificarDisponibilidade(String nome);

}
