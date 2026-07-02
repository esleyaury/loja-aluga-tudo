package com.upe.loja;

import com.upe.loja.repository.entity.Usuario.TipoPerfil;

public record Sessao(String cpf, String nome, TipoPerfil tipo) {
}
