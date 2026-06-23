package com.upe.loja.business.interfaces;

import com.upe.loja.repository.ProdutoRepository;
import com.upe.loja.repository.entity.Produto;

public interface ProdutoInterface {

    public Produto verificarDisponibilidade(ProdutoRepository produto);


}
