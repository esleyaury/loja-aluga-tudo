# loja-aluga-tudo — Módulo Cliente

Este release contém o CRUD completo de **Cliente** para o sistema "A Loja que Aluga de um Tudo", seguindo a arquitetura de 3 camadas (Apresentação / Negócio / Persistência) com padrão **Facade**, conforme especificado pelo professor.

## Estrutura

```
src/main/java/com/upe/loja/
├── Main.java                          # ponto de entrada
├── Facade.java                        # única porta de entrada da UI para a camada de negócio
├── UI/
│   └── Menu.java                      # camada de apresentação (texto/console)
├── business/
│   ├── ClienteBusiness.java           # regras de negócio (RN04, RN05, validações)
│   └── interfaces/IClienteBusiness.java
└── repository/
    ├── ClienteRepository.java         # acesso a dados em memória
    ├── GerirClientesCSV.java          # carrega/salva clientes.csv
    ├── interfaces/IClienteRepository.java
    └── entity/
        ├── Usuario.java               # classe base (cpf, senha, nome, telefone, email)
        └── Cliente.java               # extends Usuario (estado, inadimplência)
```

## Regras de negócio implementadas neste módulo

- **RN04 — Inadimplência:** `Facade.podeAlugar(cpf)` retorna `false` se o cliente estiver com a flag `inadimplente = true` (a ser setada pelo módulo de Multas/Contratos quando integrado) ou se estiver `INATIVO`.
- **RN05 — Integridade:** `remover(cpf)` realiza **exclusão lógica** (estado passa para `INATIVO`), preservando o histórico do cliente. O registro nunca é apagado do arquivo por essa via.

## Persistência

Os dados são salvos em `clientes.csv` (mesmo padrão usado no módulo de Produtos), no formato:

```
cpf;senha;nome;telefone;email;estado;inadimplente
```

O arquivo é criado automaticamente na primeira execução, carregado ao iniciar o programa e regravado ao escolher a opção "0 - Sair" no menu.

## Como compilar e rodar

Pré-requisitos: **Java 21** e **Maven** instalados.

```bash
# compilar e empacotar
mvn clean package

# rodar os testes de unidade (JUnit 5) da camada de negócio
mvn test

# executar o programa
mvn compile exec:java -Dexec.mainClass="com.upe.loja.Main"
```

Ou, alternativamente, sem plugin `exec`:

```bash
mvn clean compile
java -cp target/classes com.upe.loja.Main
```

## Integração com o sistema final

Este módulo foi desenvolvido de forma independente (com sua própria `Facade`, `Menu` e `Main`), no mesmo padrão do módulo de Produtos já existente. Na integração final do grupo, espera-se que:

- As classes `Usuario`/`Cliente` sejam reaproveitadas pelos demais CRUDs de usuário (Funcionário, Administrador).
- A `Facade` única do sistema delegue para `ClienteBusiness` (assim como já faz para `ProdutoBusiness`).
- O `Menu` principal do sistema integre as opções deste módulo ao menu unificado, substituindo o `Main` próprio deste release.
