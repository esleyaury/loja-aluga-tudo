package com.upe.loja.repository;

import com.upe.loja.repository.entity.Fornecedor;
import com.upe.loja.repository.interfaces.IFornecedorRepository;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FornecedorRepository implements IFornecedorRepository {
    private GerirCSV<Fornecedor> gerenciadorArquivo;
    private List<Fornecedor> fornecedores;
    private File arquivoFornecedores;

    public FornecedorRepository() {
        this.arquivoFornecedores = new File("fornecedores.csv");
        this.gerenciadorArquivo = new GerirCSV<>();

        // Formato da linha: cnpj;nome;telefone
        List<Fornecedor> lista = gerenciadorArquivo.carregar(this.arquivoFornecedores, linha -> {
            String[] dados = linha.split(";");
            if (dados.length != 3) { return null; }

            return new Fornecedor(dados[0], dados[1], dados[2]);
        });

        this.fornecedores = new ArrayList<>(lista);
    }

    public List<Fornecedor> listarTodos() {
        return new ArrayList<>(fornecedores);
    }

    public void salvar(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
        guardarDados();
    }

    public void remover(String cnpj) {
        fornecedores.removeIf(f->f.getCnpj().equals(cnpj));
    }

    public void atualizar(Fornecedor fornecedor) {
    }
    @Override
    public void guardarDados(){
        gerenciadorArquivo.guardarDados(this.arquivoFornecedores, this.fornecedores, f ->
            String.format("%s;%s;%s", f.getCnpj(), f.getNome(), f.getTelefone()));
    }
}
