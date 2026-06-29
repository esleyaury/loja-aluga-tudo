package com.upe.loja.repository.entity;

import java.util.UUID;

public class Cliente extends Usuario {

    private boolean inadimplente;

    public Cliente(String cpf, String senha, String nome, String email){
        // O id é gerado automaticamente (UUID) — para o Cliente, o CPF é o
        // identificador que realmente importa no dia a dia (cadastro, busca,
        // atualização e remoção são sempre feitos por CPF). O id só existe
        // "por debaixo dos panos" porque o construtor de Usuario o exige.
        this(UUID.randomUUID().toString(), cpf, senha, nome, email);
    }

    // Construtor usado apenas pelo GerenciadorClientesCSV, para restaurar o
    // id ORIGINAL do cliente ao recarregar os dados do arquivo (em vez de
    // gerar um UUID novo a cada reinício do programa). É público porque
    // Cliente (pacote .entity) e GerenciadorClientesCSV (pacote .repository)
    // estão em pacotes diferentes, mas não deve ser usado fora desse fluxo
    // de carregamento — para cadastrar um cliente novo, use sempre o
    // construtor de 4 argumentos.
    public Cliente(String id, String cpf, String senha, String nome, String email){
        super(id, cpf, senha, nome, email, TipoPerfil.CLIENTE);

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
