package com.upe.loja.repository;

import com.upe.loja.repository.entity.Categoria;

import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.util.Collections;

//Jackson JSON
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;


public class CategoriaRepository {

    private Set<String> categorias = new HashSet<>(); //usando hashset ja que so tem a string nome de categoria, ai n precisa ser hashmap
    private ObjectMapper mapper;
    private final File ARQUIVO_JSON = new File("categorias.json");

    public CategoriaRepository(){
    this.mapper = new ObjectMapper();
    this.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    this.categorias = carregar();
    }

    public Set<String> carregar() {
        // lê o arquivo JSON e popula o HashMap
        // (usando Gson ou Jackson)
        try{
            Set<String> objetoCarregado = new HashSet<>();
            objetoCarregado = mapper.readValue(ARQUIVO_JSON, new TypeReference<Set<String>>(){});
            return objetoCarregado;
        } catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    // salvar() só escreve no JSON (chamado ao encerrar o programa)
    public void salvar() {
        try {
            mapper.writerWithDefaultPrettyPrinter()
            .writeValue(ARQUIVO_JSON, categorias);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    // CRUD
    public void adicionar(Categoria categoria) {
        categorias.add(categoria.getNomeCategoria());
    }

    public boolean buscarCategoria(String nomeCategoria) {
        return categorias.contains(nomeCategoria);
    }

    public Set<String> listarCategorias() {
        return Collections.unmodifiableSet(categorias);
    }

    public void deletar(String nomeCategoria) {
        categorias.remove(nomeCategoria); //nativo do hashset
    }
}
