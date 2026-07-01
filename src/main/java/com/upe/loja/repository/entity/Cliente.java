package com.upe.loja.repository.entity;

public class Cliente extends Usuario {

    private boolean inadimplente;

    public Cliente(String cpf, String senha, String nome, String email){
        super(cpf, senha, nome, email, TipoPerfil.CLIENTE);

        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'CPF' é obrigatório.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Senha' é obrigatório.");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'Nome' é obrigatório.");
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("O campo 'Email' é obrigatório e deve ser válido.");
        }

        this.inadimplente = false;
    }

    //getters
    public boolean isInadimplente(){
        return this.inadimplente;
    }

    //setters com verificação
    public void setInadimplente(boolean inadimplente){
        this.inadimplente = inadimplente;
    }

    // RN05 - Integridade: não é possível excluir um cliente com histórico de
    // contratos. Aqui usamos o próprio campo "ativo" já existente em Usuario
    // (setAtivo/isAtivo) para fazer a exclusão lógica — sem precisar criar um
    // novo enum de estado, já que Usuario já resolve isso.
}
