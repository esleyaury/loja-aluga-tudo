package com.upe.loja.business;

import com.upe.loja.business.interfaces.IClienteBusiness;
import com.upe.loja.repository.ClienteRepository;
import com.upe.loja.repository.interfaces.IClienteRepository;
import com.upe.loja.repository.entity.Cliente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteBusiness implements IClienteBusiness {
    private IClienteRepository clientes;

    public ClienteBusiness() {
        this.clientes = new ClienteRepository();
    }

    @Override
    public void cadastrarCliente(String cpf, String senha, String nome, String email) {
        Cliente cliente = new Cliente(cpf, senha, nome, email);
        salvar(cliente);
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return clientes.buscarPorCpf(cpf);
    }

    @Override
    public List<Cliente> buscarPorNome(String nome) {
        List<Cliente> encontrados = new ArrayList<>();
        for (Cliente c : clientes.listarTodos().values()) {
            if (c.getNome().equalsIgnoreCase(nome.trim())) {
                encontrados.add(c);
            }
        }
        return encontrados;
    }

    @Override
    public void salvar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("O objeto cliente não pode ser nulo.");
        }

        // unicidade por CPF (garante que não exista cliente duplicado, já que
        // o id é gerado automaticamente e nunca colidiria por si só)
        if (clientes.buscarPorCpf(cliente.getCpf()) != null) {
            throw new IllegalArgumentException("Operação negada: Já existe um cliente cadastrado com o CPF " + cliente.getCpf());
        }

        clientes.salvar(cliente);
    }

    @Override
    public void atualizar(Cliente cliente, int option, String valor) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente inválido para atualização.");
        }
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("O novo valor não pode ser vazio.");
        }

        switch (option) {
            case 1 -> cliente.setNome(valor.trim());

            case 2 -> {
                String email = valor.trim();
                if (!email.contains("@")) {
                    throw new IllegalArgumentException("Email inválido.");
                }
                cliente.setEmail(email);
            }

            case 3 -> cliente.setSenha(valor.trim());

            case 4 -> {
                boolean novoAtivo = Boolean.parseBoolean(valor.trim());
                cliente.setAtivo(novoAtivo);
            }

            default -> throw new IllegalArgumentException("Opção de menu inválida para atualização.");
        }
        clientes.atualizar(cliente);
    }

    @Override
    public Map<String, Cliente> listarTodos() {
        return clientes.listarTodos();
    }

    @Override
    public void remover(String cpf) {
        // RN05 - Integridade: não é possível excluir um cliente com histórico
        // de contratos. Por ora este módulo não tem acesso direto à entidade
        // Contrato (de outro CRUD), então a remoção é sempre feita por
        // exclusão lógica (ativo = false), preservando o registro.
        Cliente cliente = clientes.buscarPorCpf(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        cliente.setAtivo(false);
        clientes.atualizar(cliente);
    }

    public void removerDefinitivamente(String cpf) {
        clientes.remover(cpf);
    }

    @Override
    public void guardarDados() {
        clientes.guardarDados();
    }

    @Override
    public Map<String, Cliente> clientesAtivos() {
        Map<String, Cliente> ativos = new HashMap<>();
        for (Cliente c : clientes.listarTodos().values()) {
            if (c.isAtivo()) {
                ativos.put(c.getCpf(), c);
            }
        }
        return ativos;
    }

    @Override
    public boolean podeAlugar(String cpf) {
        // RN04 - Inadimplência: Clientes com multas em aberto não podem
        // realizar novos aluguéis até que a pendência seja quitada.
        Cliente cliente = clientes.buscarPorCpf(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        return cliente.isAtivo() && !cliente.isInadimplente();
    }
}
