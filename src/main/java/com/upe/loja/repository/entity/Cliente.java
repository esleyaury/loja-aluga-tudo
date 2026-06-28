package com.upe.loja.repository.entity;

public class Cliente extends Usuario {

    private EstadoCliente estado;
    private boolean inadimplente;

    public Cliente(long cpf, String senha, String nome, long telefone, String email){
        super(cpf, senha, nome, telefone, email);
        this.estado = EstadoCliente.ATIVO;
        this.inadimplente = false;
    }

    public enum EstadoCliente{
        ATIVO,
        INATIVO // usado para exclusão lógica (RN05), quando o cliente possui histórico de contratos
    }

    //getters
    public EstadoCliente getEstado(){ return this.estado; }
    public boolean isInadimplente(){ return this.inadimplente; }

    //setters com verificação
    public void setEstado(EstadoCliente estado){
        if (estado == null) {
            throw new IllegalArgumentException("O campo 'Estado' é obrigatório.");
        }
        this.estado = estado;
    }

    public void setInadimplente(boolean inadimplente){
        this.inadimplente = inadimplente;
    }
}
