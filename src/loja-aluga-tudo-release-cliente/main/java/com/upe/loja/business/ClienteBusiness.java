package com.upe.loja.business;

import com.upe.loja.business.interfaces.IClienteBusiness;
import com.upe.loja.repository.ClienteRepository;
import com.upe.loja.repository.interfaces.IClienteRepository;
import com.upe.loja.repository.entity.Cliente;
import com.upe.loja.repository.entity.Cliente.EstadoCliente;

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
    public void cadastrarCliente(long cpf, String senha, String nome, long telefone, String email) {
        Cliente cliente = new Cliente(cpf, senha, nome, telefone, email);
        salvar(cliente);
    }

    @Override
    public Cliente buscarPorCpf(long cpf) {
        return clientes.buscarPorCpf(cpf);
    }

    @Override
    public List<Cliente> buscarPorNome(String nome) {
        List<Cliente> encontrados = this.clientes.buscarPorNome(nome);
        if (encontrados.isEmpty()) {
            return new ArrayList<>();
        }
        return encontrados;
    }

    @Override
    public void salvar(Cliente cliente) {
        // validação estrutural do objeto
        if (cliente == null) {
            throw new IllegalArgumentException("O objeto cliente não pode ser nulo.");
        }

        // unicidade (Garantir que não sobrescreva um CPF já cadastrado)
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

        // O switch centralizado na camada Business para validar cada alteração
        switch (option) {
            case 1 -> cliente.setNome(valor.trim());

            case 2 -> {
                try {
                    long telefone = Long.parseLong(valor.trim());
                    cliente.setTelefone(telefone);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Formato numérico inválido para o telefone.");
                }
            }

            case 3 -> cliente.setEmail(valor.trim());

            case 4 -> cliente.setSenha(valor.trim());

            case 5 -> {
                try {
                    // Aqui sim a String vira Enum, então tratamos com toUpperCase() e trim()
                    EstadoCliente novoEstado = EstadoCliente.valueOf(valor.toUpperCase().trim());
                    cliente.setEstado(novoEstado);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Estado inválido. Insira um estado correspondente ao sistema.");
                }
            }
            default -> throw new IllegalArgumentException("Opção de menu inválida para atualização.");
        }
        clientes.atualizar(cliente);
    }

    @Override
    public Map<Long, Cliente> listarTodos() {
        return clientes.listarTodos();
    }

    @Override
    public void remover(long cpf) {
        // RN05 - Integridade: por ora este módulo não tem acesso direto ao histórico de
        // Contratos (entidade de outro CRUD). Para preservar a regra, a remoção é feita
        // por exclusão lógica (estado INATIVO) ao invés de remover o registro do mapa.
        // Caso o cliente realmente não possua nenhum vínculo, a camada de integração final
        // (responsável pelos Contratos) pode optar por chamar removerDefinitivamente().
        Cliente cliente = clientes.buscarPorCpf(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        cliente.setEstado(EstadoCliente.INATIVO);
        clientes.atualizar(cliente);
    }

    public void removerDefinitivamente(long cpf) {
        clientes.remover(cpf);
    }

    @Override
    public void guardarDados() {
        clientes.guardarDados();
    }

    @Override
    public Map<Long, Cliente> clientesAtivos() {
        Map<Long, Cliente> todos = clientes.listarTodos();
        Map<Long, Cliente> ativos = new HashMap<>();
        for (Cliente c : todos.values()) {
            if (c.getEstado() == EstadoCliente.ATIVO) {
                ativos.put(c.getCpf(), c);
            }
        }
        return ativos;
    }

    @Override
    public boolean podeAlugar(long cpf) {
        // RN04 - Inadimplência: Clientes com multas em aberto não podem realizar novos
        // aluguéis até que a pendência seja quitada.
        Cliente cliente = clientes.buscarPorCpf(cpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        return cliente.getEstado() == EstadoCliente.ATIVO && !cliente.isInadimplente();
    }
}
