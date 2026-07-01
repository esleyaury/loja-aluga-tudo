package com.upe.loja.repository.entity;

public class Fornecedor {
    private String cnpj;
    private String nome;
    private String telefone;

    // Construtor: repare que agora ele chama os setters em vez de atribuir direto.
    // Assim, a validação já acontece logo no momento da criação (o "new Fornecedor").
    public Fornecedor(String cnpj, String nome, String telefone) {
        setCnpj(cnpj);
        setNome(nome);
        setTelefone(telefone);
    }

    // --- GETTERS ---
    public String getCnpj() {
        return cnpj;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    // --- SETTERS COM VALIDAÇÕES ---
    public void setCnpj(String cnpj) {
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("O CNPJ não pode ser vazio.");
        }
        // Opcional: Se quiser exigir exatamente 14 caracteres numéricos no futuro, a regra entraria aqui!
        this.cnpj = cnpj.trim();
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do fornecedor não pode ser vazio.");
        }
        if (nome.trim().length() < 3) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 3 caracteres.");
        }
        this.nome = nome.trim();
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone não pode ser vazio.");
        }
        this.telefone = telefone.trim();
    }
}