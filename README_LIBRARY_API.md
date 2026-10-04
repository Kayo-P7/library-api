# Library API

API REST para gerenciamento de livros e autores, desenvolvida com Java e Spring Boot como projeto de estudo e prática de desenvolvimento de APIs.

> **Status do projeto:** desenvolvimento em andamento  
> **Ponto atual do curso:** Seção 7 — Aprendendo Mais Recursos desenvolvendo a API de Livros  
> **Último vídeo concluído:** 103 — Melhorando a tratativa de erros para código mais limpo

---

## Como executar o projeto

Para gerar o build e instalar as dependências do projeto com Maven Wrapper:

```bash
./mvnw clean install
```

> **Observação:** caso o comando acima apresente falhas relacionadas aos testes, utilize `-DskipTests` para realizar o build sem executar os testes:

```bash
./mvnw clean install -DskipTests
```

O parâmetro `-DskipTests` faz com que os testes não sejam executados durante o processo de build.

# 1. Sobre a API

A aplicação tem como objetivo disponibilizar uma API REST para cadastro e gerenciamento de:

- Livros
- Autores

A API foi construída seguindo uma separação de responsabilidades entre as camadas de controller, service, repository, DTOs, entidades e mapeadores.

O projeto também está sendo utilizado para praticar conceitos importantes do ecossistema Spring, como:

- Spring Boot
- Spring Web / REST
- Spring Data JPA
- Hibernate
- Bean Validation
- MapStruct
- PostgreSQL
- DTO Pattern
- Tratamento global de exceções
- Auditoria de entidades
- Paginação
- Specifications / consultas dinâmicas

---

# 2. Arquitetura atual

A estrutura principal do projeto está organizada aproximadamente da seguinte forma:

```text
src/main/java/com/kayo/libraryapi
├── config
│   └── DatabaseConfiguration
│
├── controller
│   ├── AutorController
│   ├── LivroController
│   └── GenericController
│
├── dto
│   ├── request
│   │   ├── AutorDTO
│   │   └── CadastrarLivro
│   │
│   └── response
│       ├── ListAutorDTO
│       ├── ListExAutorDto
│       └── ResponseLivro
│
├── exception
│   ├── AutorNotFound
│   ├── ErrorCampo
│   ├── GlobalExceptionHandler
│   └── StandardError
│
├── mapper
│   ├── AutorMapper
│   └── LivroMapper
│
├── model
│   ├── Autor
│   ├── GeneroLivro
│   └── Livro
│
├── repository
│   ├── AutorRepository
│   └── LivroRepository
│
└── Services
    ├── AutorService
    ├── LivroService
    └── TransacoesService
```

A separação permite que cada camada tenha uma responsabilidade específica.

---

# 3. Entidades principais

## Autor

A entidade `Autor` representa um autor cadastrado na aplicação.

Entre os atributos atuais estão:

- `id`
- `nome`
- `dataNascimento`
- `nacionalidade`
- `dataCadastro`
- `dataAtualizacao`

O identificador utiliza `UUID`.

Exemplo conceitual:

```java
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
```

A entidade também possui auditoria JPA configurada para registrar automaticamente datas de criação e atualização.

---

## Livro

A entidade `Livro` representa os livros cadastrados.

O modelo utiliza informações como:

- ISBN
- título
- data de publicação
- gênero
- preço
- autor

O livro possui relacionamento com `Autor`, permitindo representar a associação entre livros e seus respectivos autores.

---

# 4. Auditoria

Foi adicionada auditoria utilizando o mecanismo de auditing do Spring Data JPA.

A ideia é permitir que determinadas informações sejam preenchidas automaticamente pelo framework.

Exemplo:

```java
@CreatedDate
@Column(name = "data_cadastro", nullable = false)
private LocalDateTime dataCadastro;

@LastModifiedDate
@Column(name = "data_atualizacao", nullable = false)
private LocalDateTime dataAtualizacao;
```

Com isso:

- `dataCadastro` registra quando a entidade foi criada.
- `dataAtualizacao` registra quando a entidade foi modificada.

A entidade utiliza:

```java
@EntityListeners(AuditingEntityListener.class)
```

---

# 5. DTOs

A API utiliza DTOs para evitar que as entidades JPA sejam utilizadas diretamente como contrato da API.

Existem DTOs para:

- requisições
- respostas
- cadastro
- listagem
- tratamento de erros

Isso ajuda a separar:

```text
Banco de dados
      ↓
Entidade JPA
      ↓
Mapper
      ↓
DTO
      ↓
API REST
```

Essa abordagem também evita problemas como exposição desnecessária dos campos das entidades e loops de serialização em relacionamentos.

---

# 6. MapStruct

O projeto utiliza **MapStruct** para realizar o mapeamento entre entidades e DTOs.

Atualmente existem mapeadores como:

```text
AutorMapper
LivroMapper
```

A utilização de mappers reduz código repetitivo e deixa os services mais limpos.

Exemplo conceitual:

```java
@Mapper(componentModel = "spring")
public interface AutorMapper {
    ...
}
```

Também foi estudada a composição de mappers para obter informações relacionadas durante o mapeamento.

---

# 7. Controllers

A aplicação possui controllers responsáveis pela exposição dos endpoints HTTP.

Atualmente existem:

```text
AutorController
LivroController
GenericController
```

O `GenericController` foi introduzido para reduzir repetição de código e facilitar a criação de operações comuns.

---

# 8. API de Autores

A API possui operações relacionadas ao cadastro e gerenciamento de autores.

Entre as funcionalidades implementadas até o ponto atual estão:

- Cadastro de autor
- Busca de autor
- Listagem de autores
- Busca de autores com filtros
- Atualização de autor
- Exclusão de autor
- Validação dos dados
- Tratamento de autor inexistente
- Respostas padronizadas de erro

Também foi implementada pesquisa de autores utilizando recursos do Spring Data JPA.

---

# 9. API de Livros

A API de livros possui operações para gerenciamento dos livros.

Entre as funcionalidades trabalhadas até o vídeo 103:

- Cadastro de livro
- Consulta de livro
- Listagem
- Atualização
- Exclusão
- Validação dos dados
- Validação de ISBN duplicado
- Validação da data/ano de publicação
- Paginação
- Relacionamento com autor
- Retorno de dados através de DTOs
- Tratamento padronizado de erros

---

# 10. Contratos da API

A API está sendo desenvolvida com contratos HTTP bem definidos.

Exemplo de consulta de detalhes:

```text
GET /livros/{id}
```

Resposta de sucesso:

```http
200 OK
```

Exemplo conceitual de resposta:

```json
{
  "id": "uuid",
  "isbn": "string",
  "titulo": "string",
  "dataPublicacao": "date",
  "genero": "enum",
  "preco": 0,
  "autor": {
    "nome": "string",
    "dataNascimento": "date",
    "nacionalidade": "string"
  }
}
```

Quando o recurso não é encontrado, a API utiliza:

```http
404 Not Found
```

---

# 11. Bean Validation

Foi implementada validação de dados utilizando Bean Validation.

A validação é utilizada para garantir que as requisições recebidas pela API respeitem as regras definidas.

Entre as validações estudadas/implementadas:

- Campos obrigatórios
- Tamanho de campos
- Formato de dados
- Regras específicas de negócio
- Validação de ISBN
- Validação de data de publicação

Também foi criado um `ExceptionHandler` específico para lidar com erros de validação.

---

# 12. Tratamento de exceções

A aplicação possui tratamento global de exceções através de:

```text
GlobalExceptionHandler
```

Além disso, existem estruturas para representar os erros:

```text
StandardError
ErrorCampo
```

O objetivo é impedir que a API retorne respostas inconsistentes quando ocorre uma exceção.

A ideia é padronizar respostas como:

```json
{
  "timestamp": "2026-10-04T14:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Autor não encontrado",
  "path": "/autores/..."
}
```

Erros de validação podem apresentar os campos que causaram o problema.

---

# 13. Regras de negócio

Além das validações básicas, a API possui regras de negócio.

Exemplos:

- Não permitir ISBN duplicado.
- Não permitir determinadas datas de publicação inválidas.
- Verificar se o autor existe antes de associá-lo a um livro.
- Retornar erro apropriado quando um recurso não existe.

As regras ficam concentradas principalmente na camada de service, evitando colocar lógica de negócio diretamente nos controllers.

---

# 14. Camada Service

As principais classes de serviço são:

```text
AutorService
LivroService
TransacoesService
```

A camada Service é responsável por coordenar as operações da aplicação.

Fluxo simplificado:

```text
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
Repository
     ↓
Database
```

Na resposta:

```text
Database
     ↓
Repository
     ↓
Service
     ↓
Mapper
     ↓
DTO
     ↓
Controller
     ↓
HTTP Response
```

---

# 15. Repository

A aplicação utiliza Spring Data JPA para acesso ao banco.

Principais repositories:

```text
AutorRepository
LivroRepository
```

Eles são responsáveis pelas operações de persistência e consulta.

Também foram estudados recursos como:

- Query Methods
- `Query By Example`
- Specifications
- consultas com filtros
- paginação

---

# 16. Paginação

A API possui suporte a consultas paginadas.

A paginação é importante principalmente para endpoints de listagem, evitando retornar uma quantidade potencialmente muito grande de registros de uma única vez.

Conceitualmente:

```text
GET /livros?page=0&size=10
```

O índice da página começa em `0`.

---

# 17. Specifications

A partir da seção 7, o projeto começou a explorar recursos mais avançados de consulta do Spring Data JPA.

Foram estudados:

- `Specification`
- filtros dinâmicos
- composição de Specifications
- `JpaSpecificationExecutor`

Esses recursos permitem montar consultas de acordo com os parâmetros enviados pelo cliente.

Exemplo conceitual:

```text
GET /livros?titulo=Java&genero=FICCAO
```

A API pode combinar os filtros para construir a consulta necessária.

---

# 18. Generic Controller

Foi criado um controller genérico para reduzir duplicação de código entre controllers.

A ideia é utilizar generics para abstrair operações que possuem comportamento semelhante.

Estrutura conceitual:

```java
GenericController<T, ID>
```

Isso permite reutilizar operações comuns para diferentes recursos da API.

---

# 19. Transações

O projeto possui uma classe:

```text
TransacoesService
```

para trabalhar com operações transacionais.

Transações são importantes quando uma operação envolve várias alterações que precisam ser tratadas como uma única unidade.

Exemplo conceitual:

```text
Início da transação
       ↓
Operação 1
       ↓
Operação 2
       ↓
Operação 3
       ↓
Commit
```

Se ocorrer uma falha durante a operação, o comportamento transacional pode evitar que o banco fique em um estado inconsistente.

---

# 20. Tecnologias utilizadas

| Tecnologia | Utilização |
|---|---|
| Java | Linguagem principal |
| Spring Boot | Framework da aplicação |
| Spring Web | API REST |
| Spring Data JPA | Persistência |
| Hibernate | ORM |
| PostgreSQL | Banco de dados |
| Bean Validation | Validação |
| MapStruct | Mapeamento Entity/DTO |
| Lombok | Redução de código boilerplate |
| Maven | Gerenciamento de dependências |
| Git | Controle de versão |
| GitHub | Hospedagem do código |

---

# 21. Progresso no curso

## Seção 6 — Introdução ao Bean Validation e Melhorias para API

Concluída.

Conteúdos vistos:

- Bean Validation
- Validação de campos
- Exception Handler
- Tipos de validação no Spring
- Query By Example

## Seção 7 — Aprendendo Mais Recursos desenvolvendo a API de Livros

**Em andamento.**

Conteúdos concluídos até o vídeo 103:

- Especificação da API
- Estruturas básicas da API
- Endpoint de cadastro de livro
- MapStruct
- Mapper
- Salvamento de livro no banco
- Interface genérica
- Tratamento de erros
- Detalhes do livro
- Composição de mappers

### Próximo conteúdo

O próximo vídeo é:

```text
104 — Obtendo detalhes do livro - Mapstruct utilizando composição de outro mapper
```

Depois ainda estão previstos conteúdos como:

- Contrato de exclusão de livros
- Specifications do Spring Data JPA
- Specifications para Livro
- Funções do banco através de Specification
- Joins
- Atualização de livros
- Validação de ISBN duplicado
- Validação do ano/data de publicação
- Pesquisa paginada

---

# 22. Estado atual

Neste momento a API já possui uma estrutura bastante próxima de uma aplicação REST organizada em camadas.

### Já implementado

- [x] Estrutura Spring Boot
- [x] Entidade Autor
- [x] Entidade Livro
- [x] Repositories
- [x] Services
- [x] Controllers
- [x] DTOs
- [x] MapStruct
- [x] Relacionamento entre Livro e Autor
- [x] Auditoria JPA
- [x] Bean Validation
- [x] Exception Handler
- [x] Respostas padronizadas de erro
- [x] Regras de negócio
- [x] Paginação
- [x] Generic Controller
- [x] Query By Example
- [x] Introdução a Specifications
- [x] Operações de CRUD
- [x] Busca de autores
- [x] Validações de ISBN/data

### Ainda em desenvolvimento

- [ ] Finalização da implementação de Specifications
- [ ] Joins utilizando Specifications
- [ ] Atualização completa de livros
- [ ] Finalização das regras de validação
- [ ] Spring Security
- [ ] JWT
- [ ] OAuth2
- [ ] Docker
- [ ] Deploy na AWS
- [ ] Testes automatizados

> Os itens acima fazem parte do desenvolvimento posterior da aplicação/curso e ainda não devem ser considerados funcionalidades concluídas neste ponto do projeto.

---

# 23. Objetivo do projeto

O objetivo não é apenas criar um CRUD simples.

O projeto está sendo utilizado para desenvolver domínio sobre:

- construção de APIs REST;
- arquitetura em camadas;
- persistência com JPA/Hibernate;
- relacionamentos entre entidades;
- DTOs;
- mapeamento com MapStruct;
- validação;
- tratamento de exceções;
- regras de negócio;
- consultas avançadas;
- paginação;
- Specifications;
- generics;
- boas práticas de desenvolvimento com Spring Boot.

A API continuará evoluindo conforme o desenvolvimento das próximas seções do projeto.
