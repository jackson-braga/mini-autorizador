# Padrões e Convenções - mini-autorizador

Este documento descreve os padrões e convenções adotados no projeto.

## Estrutura de pacotes

src/main/java/br/com/jfintech/mini_autorizador/
- config/                -> Configurações do Spring Boot (security, beans, OpenAPI, DB custom)
- exception/             -> Tratamento global de exceções e exceções de negócio
    - ApplicationExceptionHandler.java
    - BusinessException.java
- controller/            -> CAMADA HTTP (versionamento em `v1`)
    - v1/
        - CartaoController.java
        - request/           -> DTOs de entrada (validações com jakarta/hibernate-validator)
        - response/          -> DTOs de saída
- model/
    - entity/              -> Entidades JPA (@Entity) — nomes no singular (CartaoEntity)
    - mapper/              -> MapStruct/ModelMapper para conversões (entidade <-> dto)
- repository/            -> Interfaces Spring Data (JpaRepository)
- service/               -> Lógica de negócio (preferência por interfaces + impls)
    - ConsultaSaldoService.java

## Convenções importantes

- Pacotes seguem o padrão reverse-domain: br.com.jfintech.mini_autorizador.
- Classes: PascalCase (CartaoController, CartaoEntity).
- Interfaces de serviço: sufixo `Service` (ex: CartaoService). Implementações: `*ServiceImpl`.
- Repositórios: sufixo `Repository` e estender `JpaRepository<Entity, ID>`.
- DTOs: colocar em `controller.v1.request` e `controller.v1.response` ou em `dto` se preferir desacoplar.
    - Validações: usar jakarta.validation nas records de request. Exemplo (CartaoRequest):
        - numeroCartao: @NotBlank, @Pattern(regexp = "\\d{16}")
        - senha: @NotBlank, @Pattern(regexp = "\\d{4}")
- MapStruct recomendado para mappers: anotar com `@Mapper(componentModel = "spring")`.

## Premissas e Regras de Desenvolvimento

Estas regras são obrigatório por padrão em todo o projeto:

- Injeção de dependência
    - Todas as injeções devem ser feitas via construtor (constructor injection).
    - Campos que recebem dependências devem ser `private` e `final`.
    - Usar Lombok `@RequiredArgsConstructor` nas classes que recebem dependências para gerar o construtor.

- Entidades JPA
    - Entidades devem manter o mapeamento JPA (`@Entity`, `@Table`, `@Id`, etc.).
    - Usar Lombok nas entidades: `@Data`, `@Builder`, `@NoArgsConstructor` e `@AllArgsConstructor`.
    - Garantir preservação de um construtor sem-args para JPA.

- Controllers
    - Controllers devem apenas orquestrar, delegando lógica ao service.
    - Controllers não devem manipular ou expor entidades JPA diretamente; os services devem retornar DTOs/records de resposta.

- Serviços
    - Preferir definir interfaces (`*Service`) e implementações (`*ServiceImpl`) para facilitar testes e desacoplamento.
    - Padrão de orquestração: um serviço orquestrador (`CadastraCartaoService`, `ConsultaCartaoService`) coordena a lógica específica da operação.
    - O `CartaoRepository` expõe consultas específicas por intenção de negócio, como `findSaldoByNumeroCartao(String numeroCartao)`, retornando `Optional<BigDecimal>` para evitar carregamento completo da entidade.
    - Cada serviço tem uma única responsabilidade (Single Responsibility Principle).
    - Benefícios: testes isolados por camada, reutilização de serviços, clareza de fluxo.

### Fluxo completo do método `realizarTransacao`

O fluxo de autorização de transação segue a seguinte sequência no endpoint `POST /transacoes`:

1. `TransacaoController.realizarTransacao`
   -recebe o payload validado por `@Valid` em `TransacaoRequest`;
   -chama `RealizaTransacaoService.realizarTransacao(request)`;
   -retorna `ResponseEntity.status(HttpStatus.CREATED)` com o resultado (`"OK"`).

2. `RealizaTransacaoServiceImpl.realizarTransacao`
    - esta camada é a orquestradora do processo;
    - delega a execução para `RealizarTransacaoChain.processar(request)`;
    - a anotação `@Transactional` garante que toda a operação seja executada como uma única transação.

3. `RealizarTransacaoChain.processar`
    - cria um `RealizaTransacaoContext` a partir do request;
    - popula o contexto com o `request` e o estado da execução;
    - executa todos os steps em sequência, representando o padrão `Chain of Responsibility`.

4. Passos da cadeia:
    - `BuscaCartaoStep`
        - busca o cartão por número na base com `CartaoRepository`;
        - se não encontrado, lança `SaldoInvalidoException("Cartão inexistente", "CARTAO_INEXISTENTE")`;
    - `ValidaSenhaStep`
        - compara a senha informada com a senha do cartão;
        - se inválida, lança `SaldoInvalidoException("Senha inválida", "SENHA_INVALIDA")`;
    - `ValidaSaldoStep`
        - verifica se o saldo do cartão é suficiente para o valor da transação;
        - se não houver saldo, lança `SaldoInvalidoException("Saldo insuficiente", "SALDO_INSUFICIENTE")`;
    - `CalculaNovoSaldoStep`
        - calcula o saldo atualizado após o débito;
        - atualiza o `novoSaldo` no contexto;
    - `PersisteSaldoStep`
        - persiste o saldo atualizado no `CartaoRepository` via `atualizarSaldoPorNumeroCartao`;
    - `PersisteTransacaoStep`
        - converte a requisição para `TransacaoEntity` usando `TransacaoMapper`;
        - salva a transação em `TransacaoRepository`.

5. Resultado final
    - ao concluir todos os passos sem exceções, o fluxo retorna a string `"OK"`;
    - o `ApplicationExceptionHandler` converte exceções de negócio para respostas HTTP 422 e mensagens padronizadas (`CARTAO_INEXISTENTE`, `SENHA_INVALIDA`, `SALDO_INSUFICIENTE`).

6. Observações de concorrência
    - para garantir consistência em cenários simultâneos, a entidade `CartaoEntity` deve usar controles de concorrência adequados (por exemplo `@Version` ou bloqueio pessimista com `PESSIMISTIC_WRITE`), pois o fluxo de leitura + validação + atualização do saldo não é atômico por padrão.
    - a operação de busca do cartão em `BuscaCartaoStep` deve ser protegida em situações de alta concorrência para evitar race conditions em duas transações concorrentes sobre o mesmo cartão.


- DTOs e Validação
    - Usar DTOs para entrada/saída; nunca expor entidades JPA diretamente na API.
    - Validar DTOs com jakarta (`@NotNull`, `@Size`, `@Pattern`, etc.).

- Tratamento de Erros
    - Usar `@ControllerAdvice` para centralizar tratamento de exceções.

- Testes
    - Unitários: JUnit 5 + Mockito.
    - Integração: SpringBootTest
    - Nas unit tests preferir mocks para dependências e testar serviços isoladamente. 
    - Seguir as premissas gerais de testes given/when/then
    - Utilizar fixtures para inicializar dados de teste
    - Evitar any() nos testes, 
    - Adicionar verify obrigatoriamente para todos os métodos mockados.
    - Padrão para testes de controllers:
        - Usar `@WebMvcTest(controllers = SuaController.class)` (importar de `org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest`) para carregar apenas o contexto da controller.
        - Incluir explicitamente o `ApplicationExceptionHandler` com `@Import(ApplicationExceptionHandler.class)` para que o `@ControllerAdvice` seja carregado.
        - Utilizar `@MockitoBean` (importar de `org.springframework.test.context.bean.override.mockito.MockitoBean`) para mockar dependências do controller (services).

Fixtures de teste

- Padrão Fixture
    - Fixtures de código (Builders / Object Mothers) devem ficar em src/test/java/br/com/jfintech/mini_autorizador/fixture.
    - Use methods estáticos para criar objetos prontos para uso nos testes (ex: CartaoFixture.criarValido()).

## Tratamento de erros

- Exceções de negócio: criar classes que estendam `RuntimeException` (ex: BusinessException).
- Handler global com `@ControllerAdvice` (ApplicationExceptionHandler.java) para:
    - mapear BusinessException -> 422
    - mapear ConstraintViolationException / MethodArgumentNotValidException -> 400
    - mapear exceptions inesperadas -> 500

## Validação e segurança

- jakarta para validação de DTOs (`@NotNull`, `@Size`, `@Pattern`).
- Segurança: concentrar configuração em `config/` usando Spring Security.

## Documentação da API

- OpenAPI/Swagger em `config/` para gerar documentação e facilitar testes.
