package com.upe.loja;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

// [CORREÇÃO ANTIGRAVITY] Import concreto mantido apenas para instanciação interna.
import com.upe.loja.business.ProdutoBusiness;
// [CORREÇÃO ANTIGRAVITY] Facade agora referencia a interface, não a classe concreta.
// Isso garante que a camada de apresentação (UI) só interage com a camada de
// negócio por meio de interface, respeitando a arquitetura de 3 camadas.
import com.upe.loja.business.interfaces.IProdutoBusiness;
import com.upe.loja.repository.entity.Produto;

public class Facade {
    // [CORREÇÃO ANTIGRAVITY] Tipo alterado de ProdutoBusiness (concreto) para
    // IProdutoBusiness (interface). A Facade deve depender apenas da interface
    // da camada de negócio.
    private final IProdutoBusiness produtoBusiness;

    // [CORREÇÃO ANTIGRAVITY] Construtor alterado: antes recebia ProdutoBusiness
    // por parâmetro (forçando a Main a conhecer a camada de negócio). Agora cria
    // internamente, seguindo o requisito: "A UI não deve instanciar regras de
    // negócio ou repositórios diretamente."
    public Facade(){
        this.produtoBusiness = new ProdutoBusiness();
    }

    // [CORREÇÃO ANTIGRAVITY] Método refatorado: antes criava o objeto Produto aqui
    // dentro do Facade (new Produto(...)). Isso violava a arquitetura porque a
    // criação de entidades com regras de validação é responsabilidade da camada de
    // negócio, não da Facade. Agora o Facade apenas delega os dados primitivos para
    // o Business, que decide como criar e validar o Produto.
    public void cadastrarProduto(String id, String nome, BigDecimal taxaDiaria,
        String conservacao, BigDecimal valorReposicao){
        produtoBusiness.cadastrarProduto(id, nome, taxaDiaria, conservacao, valorReposicao);
    }

    public List<Produto> verificarDisponibilidade(String nome){
        return produtoBusiness.verificarDisponibilidade(nome);
    }

    public Produto buscarPorId(String id){
        return produtoBusiness.buscarPorId(id);
    }

    public void atualizarProduto(Produto produto, int option, String valor){
        produtoBusiness.atualizar(produto, option, valor);
    }

    public Map<String, Produto> listarTodos(){
        return produtoBusiness.listarTodos();
    }

    public void removerProduto(String id){
        produtoBusiness.remover(id);
    }

    public void fecharPrograma(){
        produtoBusiness.guardarDados();
    }

}
