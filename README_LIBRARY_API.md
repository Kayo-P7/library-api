# Library API

API REST para gerenciamento de livros, autores e usuários, desenvolvida com Java e Spring Boot.

## Sobre o projeto

O projeto tem como objetivo praticar o desenvolvimento de uma API REST utilizando o ecossistema Spring, aplicando conceitos de arquitetura em camadas, persistência de dados, DTOs, validação, tratamento de exceções, consultas dinâmicas, autenticação e autorização.

## Tecnologias

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Security
- PostgreSQL
- Bean Validation
- MapStruct
- Lombok
- Thymeleaf
- Maven
- Git/GitHub

## Funcionalidades

### Autores

- Cadastro de autores
- Busca de autor por ID
- Listagem de autores
- Pesquisa por nome
- Pesquisa por nacionalidade
- Exclusão de autores
- Validação de dados
- Controle de acesso baseado em roles

### Livros

- Cadastro de livros
- Busca de livros
- Listagem de livros
- Atualização de livros
- Exclusão de livros
- Validação de ISBN
- Validação de data de publicação
- Relacionamento entre livros e autores
- Paginação
- Filtros dinâmicos
- Specifications
- Query By Example

### Usuários

- Cadastro de usuários
- Persistência de usuários no banco de dados
- Criptografia de senhas com BCrypt
- Autenticação utilizando Spring Security
- Autorização baseada em roles

## Arquitetura

O projeto está organizado em camadas:

    src/main/java/com/kayo/libraryapi
    ├── config
    │   ├── DatabaseConfiguration
    │   ├── SecurityConfiguration
    │   └── WebConfiguration
    │
    ├── controller
    │   ├── AutorController
    │   ├── GenericController
    │   ├── LivroController
    │   ├── LoginController
    │   ├── UserDetailsImpl
    │   ├── UserDetailsImplService
    │   └── UsuarioController
    │
    ├── dto
    │   ├── request
    │   │   ├── AutorDTO
    │   │   ├── CadastrarLivro
    │   │   └── CadastrarUsuario
    │   │
    │   └── response
    │       ├── ListAutorDTO
    │       ├── ListExAutorDto
    │       └── LivroResponse
    │
    ├── exception
    │   ├── AutorNotFound
    │   ├── ErrorCampo
    │   ├── GlobalExceptionHandler
    │   └── StandardError
    │
    ├── mapper
    │   ├── AutorMapper
    │   ├── LivroMapper
    │   └── UsuarioMapper
    │
    ├── model
    │   ├── Autor
    │   ├── GeneroLivro
    │   ├── Livro
    │   └── Usuario
    │
    ├── repository
    │   ├── AutorRepository
    │   ├── LivroRepository
    │   ├── SpecsLivro
    │   └── UsuarioRepository
    │
    └── Services
        ├── AutorService
        ├── LivroService
        ├── TransacoesService
        └── UsuarioService

## DTOs

O projeto utiliza DTOs para separar os objetos utilizados pela API das entidades persistidas no banco de dados.

### DTOs de Request

São utilizados para receber dados enviados pelo cliente.

Exemplos:

- `AutorDTO`
- `CadastrarLivro`
- `CadastrarUsuario`

### DTOs de Response

São utilizados para controlar os dados retornados pela API.

Exemplos:

- `ListAutorDTO`
- `ListExAutorDto`
- `LivroResponse`

Fluxo de entrada:

    Request
       ↓
    DTO
       ↓
    Mapper
       ↓
    Entity
       ↓
    Repository

Fluxo de saída:

    Repository
       ↓
    Entity
       ↓
    Mapper
       ↓
    DTO
       ↓
    Response

## MapStruct

Foi implementado o uso do MapStruct para realizar o mapeamento entre DTOs e entidades.

Mappers utilizados:

- `AutorMapper`
- `LivroMapper`
- `UsuarioMapper`

O MapStruct permite centralizar a conversão entre os objetos e reduzir código repetitivo.

Além do mapeamento simples, o `LivroMapper` possui mapeamentos personalizados para campos que não devem ser preenchidos diretamente ou que dependem de outras informações.

Por exemplo, o relacionamento entre livro e autor é tratado durante o mapeamento, utilizando o `AutorRepository` para localizar o autor correspondente.

## Spring Security

Foi implementada uma configuração própria de segurança utilizando Spring Security.

A configuração está centralizada em `SecurityConfiguration`.

Foram adicionadas as seguintes configurações:

- `@Configuration`
- `@EnableWebSecurity`
- `@EnableMethodSecurity`
- `SecurityFilterChain`
- `PasswordEncoder`

O `@EnableMethodSecurity` habilita a segurança em nível de método, permitindo utilizar anotações como `@PreAuthorize`, `@Secured` e anotações JSR-250.

## SecurityFilterChain

A aplicação possui uma configuração personalizada através de `SecurityFilterChain`.

A configuração atual possui:

- CSRF desabilitado
- Form Login
- HTTP Basic
- Cadastro de usuário liberado
- Demais endpoints protegidos por autenticação

O endpoint `POST /usuario` pode ser acessado sem autenticação para permitir o cadastro de novos usuários.

Os demais endpoints exigem um usuário autenticado.

## Autenticação

Foi implementado um fluxo de autenticação utilizando usuários armazenados no banco de dados.

Foram adicionados:

- `UserDetailsImpl`
- `UserDetailsImplService`
- `UsuarioRepository`
- `UsuarioService`

O `UserDetailsImplService` é responsável por localizar o usuário no banco de dados e fornecer as informações necessárias para o Spring Security realizar a autenticação.

Fluxo:

    Login
      ↓
    Spring Security
      ↓
    UserDetailsService
      ↓
    UsuarioRepository
      ↓
    Usuario
      ↓
    UserDetails
      ↓
    Autenticação

## Criptografia de senhas

As senhas dos usuários são protegidas utilizando BCrypt através do `PasswordEncoder`.

A configuração utiliza `BCryptPasswordEncoder`.

No cadastro do usuário, a senha recebida pelo DTO é criptografada antes de ser persistida.

Fluxo:

    Senha original
          ↓
    PasswordEncoder
          ↓
    BCrypt
          ↓
    Hash da senha
          ↓
    Banco de dados

A senha original não é armazenada diretamente no banco de dados.

## Autorização por Roles

Além da autenticação, foi implementado controle de autorização baseado em roles.

Os Controllers utilizam `@PreAuthorize` para determinar quais perfis podem executar determinadas operações.

Exemplo de regra utilizada:

`@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'USER')")`

Para operações administrativas:

`@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")`

Dessa forma, diferentes operações podem possuir diferentes níveis de acesso.

### Roles utilizadas

- `USER`
- `GERENTE`
- `ADMIN`

Exemplo de comportamento:

- `USER`: acesso às operações permitidas para usuários comuns
- `GERENTE`: acesso às operações permitidas para gerentes
- `ADMIN`: acesso às operações administrativas

## Login com Thymeleaf

Foi criada uma página de login utilizando Thymeleaf.

Arquivo:

`src/main/resources/templates/login.html`

A rota `/login` é registrada através da configuração MVC da aplicação.

A `WebConfiguration` utiliza um `ViewController` para direcionar a rota `/login` para o template de login.

Isso permite utilizar uma página própria de login integrada ao Spring Security.

## Specifications

Foi implementado o uso de `Specification` do Spring Data JPA para construir consultas dinâmicas.

A classe responsável pelos filtros é:

`SpecsLivro`

Foram criadas Specifications para diferentes critérios de pesquisa, como:

- ISBN
- Título
- Gênero
- Ano de publicação
- Nome do autor

Isso permite combinar diferentes filtros sem precisar criar um método diferente no Repository para cada combinação possível.

## Filtros de livros

A busca de livros pode utilizar filtros dinâmicos.

Entre os filtros implementados estão:

- ISBN
- Título
- Gênero
- Ano de publicação
- Autor

As Specifications utilizam a API Criteria do JPA para construir as consultas.

Também foram utilizadas operações como `LIKE` e `UPPER` para permitir pesquisas textuais sem depender diretamente da capitalização informada pelo usuário.

## Relacionamento entre Livro e Autor

O projeto possui relacionamento entre livros e autores.

Durante o mapeamento de um novo livro, o `LivroMapper` pode utilizar o `AutorRepository` para localizar o autor informado no cadastro.

Dessa forma, o DTO de cadastro não precisa receber diretamente uma entidade `Autor`.

Fluxo:

    CadastrarLivro
          ↓
    LivroMapper
          ↓
    AutorRepository
          ↓
    Autor
          ↓
    Livro
          ↓
    LivroRepository

## Tratamento global de exceções

Foi implementado um tratamento global de exceções através da classe `GlobalExceptionHandler`.

A classe utiliza `@RestControllerAdvice` para centralizar o tratamento dos erros da aplicação.

Entre os erros tratados estão:

- Autor não encontrado
- Erros de validação
- Acesso negado
- Erros inesperados

## Erros de validação

Erros provenientes de `MethodArgumentNotValidException` são convertidos para uma estrutura padronizada.

Cada erro possui informações como:

- Campo
- Mensagem de erro

Isso permite que a API retorne respostas mais organizadas para erros de validação.

## Acesso negado

Também foi implementado tratamento para `AccessDeniedException`.

Quando um usuário autenticado tenta acessar um recurso para o qual não possui autorização, a API retorna uma resposta HTTP `403 Forbidden`.

Isso integra o tratamento de erros da aplicação ao sistema de autorização do Spring Security.

## Resposta padronizada de erros

A aplicação utiliza as classes:

- `StandardError`
- `ErrorCampo`

para manter um formato padronizado nas respostas de erro.

As respostas possuem informações como:

- Timestamp
- Status HTTP
- Título do erro
- Mensagem
- URI da requisição
- Erros específicos dos campos, quando aplicável

## WebConfiguration

Foi criada uma configuração MVC através de `WebConfiguration`.

Ela é responsável por registrar a rota da página de login.

A configuração utiliza:

- `@Configuration`
- `@EnableWebMvc`
- `WebMvcConfigurer`
- `ViewControllerRegistry`

A rota `/login` é direcionada para o template `login.html`.

## Controle de acesso nos Controllers

As operações dos Controllers passaram a utilizar autorização baseada em roles.

Exemplo:

`@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'USER')")`

Operações administrativas podem utilizar:

`@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")`

Isso permite que a regra de autorização fique próxima da operação que está sendo protegida.

## Banco de dados

A aplicação utiliza PostgreSQL como banco de dados e Spring Data JPA/Hibernate para persistência.

Os Repositories utilizados incluem:

- `AutorRepository`
- `LivroRepository`
- `UsuarioRepository`

A persistência segue o fluxo:

    Controller
       ↓
    Service
       ↓
    Repository
       ↓
    JPA / Hibernate
       ↓
    PostgreSQL

## Services

A lógica de negócio está organizada em Services.

Services presentes no projeto:

- `AutorService`
- `LivroService`
- `UsuarioService`
- `TransacoesService`

Os Controllers delegam as operações de negócio para os Services, evitando concentrar regras diretamente nos Controllers.

## Transações

O projeto possui um Service específico para operações relacionadas a transações:

`TransacoesService`

Essa separação permite centralizar operações que necessitam de controle transacional.

## Testes

O projeto possui testes utilizando o ecossistema de testes do Spring.

Entre os testes presentes estão:

- `AutorRepositoryTest`
- `LivroRepositoryTest`
- `TransacoesTest`
- `ApplicationTests`

Os testes são utilizados para verificar o comportamento da aplicação e principalmente das operações relacionadas à persistência.

## Objetivo do projeto

Este projeto está sendo utilizado como prática para aprofundar conhecimentos em:

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- DTOs
- MapStruct
- Bean Validation
- Tratamento de exceções
- Specifications
- Query By Example
- Spring Security
- Autenticação
- Autorização
- Roles
- BCrypt
- Thymeleaf
- Testes automatizados
- Arquitetura em camadas

## Próximos passos

Possíveis evoluções para o projeto:

- Melhorar a documentação da API
- Adicionar Swagger/OpenAPI
- Aumentar a cobertura de testes
- Criar testes para autenticação e autorização
- Melhorar o tratamento de exceções do Spring Security
- Implementar paginação e ordenação de forma mais completa
- Adicionar migrations com Flyway
- Criar Dockerfile
- Criar Docker Compose
- Melhorar o gerenciamento de configurações e secrets
- Evoluir a autenticação para JWT
- Implementar refresh token
- Adicionar observabilidade e logs estruturados

## Status

Projeto em desenvolvimento e utilizado como projeto de estudo para aprofundamento em desenvolvimento Backend com Java e Spring Boot.