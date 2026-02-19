# 🚀 Desafio Técnico -- API REST com Quarkus -- ADA

## 🎯 Objetivo

Desenvolver uma API REST utilizando **Quarkus** para gerenciar
**Cursos** e suas **Aulas**.

A aplicação deve expor endpoints REST seguindo boas práticas de:

-   REST
-   Validação
-   Códigos de status HTTP
-   Headers corretos

------------------------------------------------------------------------

# 📊 Sistema de Pontuação

**Total de Pontos Obrigatórios: 100 pontos**
**Pontos Opcionais: 1 ponto cada item**

### Legenda
- 🔴 **Obrigatório** - Conta para os 100 pontos base
- 🟢 **Opcional (Plus)** - 1 ponto extra cada

------------------------------------------------------------------------

# 📦 Modelo de Dados

## 📚 Course (Curso) - 🔴 **[10 pontos | Peso: 1.0]**

-   `id` (Long)
-   `name` (String)
-   `lessons` (List`<Lesson>`{=html})

## 📖 Lesson (Aula) - 🔴 **[5 pontos | Peso: 0.5]**

-   `id` (Long)
-   `name` (String)

------------------------------------------------------------------------

# 🔧 Requisitos Funcionais - 🔴 **[25 pontos | Peso: 2.5]**

## 1️⃣ CRUD de Curso

A API deve permitir:

-   **Criar curso** - 🔴 [5 pontos | Peso: 0.5]
-   **Listar todos os cursos** - 🔴 [5 pontos | Peso: 0.5]
-   **Buscar curso por ID** - 🔴 [5 pontos | Peso: 0.5]
-   **Atualizar curso** - 🔴 [5 pontos | Peso: 0.5]
-   **Remover curso** - 🔴 [5 pontos | Peso: 0.5]

------------------------------------------------------------------------

# 📡 Endpoints Esperados

### Criar Curso - 🔴 **[3 pontos | Peso: 0.3]**

POST /courses

### Listar Cursos - 🔴 **[3 pontos | Peso: 0.3]**

GET /courses

### Buscar por ID - 🔴 **[3 pontos | Peso: 0.3]**

GET /courses/{id}

### Atualizar Curso - 🔴 **[3 pontos | Peso: 0.3]**

PUT /courses/{id}

### Remover Curso - 🔴 **[3 pontos | Peso: 0.3]**

DELETE /courses/{id}

### Criar Aula dentro de um Curso - 🟢 **[3 pontos | Peso: 0.3]** (Plus)

POST /courses/{courseId}/lessons

### Listar todas as aulas de um curso - 🟢 **[3 pontos | Peso: 0.3]** (Plus)

GET /courses/{courseId}/lessons

------------------------------------------------------------------------

# 🧾 Regras de Validação - 🔴 **[15 pontos | Peso: 1.5]**

## Course - 🔴 **[10 pontos | Peso: 1.0]**

-   `name`:
    -   **Obrigatório** - 🔴 [3 pontos | Peso: 0.3]
    -   **Não pode ser vazio** - 🔴 [3 pontos | Peso: 0.3]
    -   **Mínimo 3 caracteres** - 🔴 [4 pontos | Peso: 0.4]

## Lesson - 🔴 **[5 pontos | Peso: 0.5]**

-   `name`:
    -   **Obrigatório** - 🔴 [3 pontos | Peso: 0.3]
    -   **Não pode ser vazio** - 🔴 [2 pontos | Peso: 0.2]

------------------------------------------------------------------------

# 🌐 Requisitos HTTP (Muito Importante) - 🔴 **[30 pontos | Peso: 3.0]**

## Content-Type - 🔴 **[10 pontos | Peso: 1.0]**

A API deve:

-   **Aceitar apenas `application/json`** - 🔴 [4 pontos | Peso: 0.4]
-   **Se o cliente enviar um `Content-Type` diferente, retornar `415 Unsupported Media Type`** - 🔴 [3 pontos | Peso: 0.3]
-   **Retornar `Content-Type: application/json` nas respostas** - 🔴 [3 pontos | Peso: 0.3]

------------------------------------------------------------------------

## Status Codes Esperados - 🔴 **[20 pontos | Peso: 2.0]**

### POST /courses - 🔴 **[5 pontos | Peso: 0.5]**

-   **`201 Created` → Curso criado com sucesso** - 🔴 [3 pontos | Peso: 0.3]
-   **Retornar 201 com header `Location`** - 🟢 [3 pontos | Peso: 0.3] (Plus)
-   **`400 Bad Request` → Dados inválidos** - 🔴 [2 pontos | Peso: 0.2]

------------------------------------------------------------------------

### GET /courses/{id} - 🔴 **[4 pontos | Peso: 0.4]**

-   **`200 OK` → Encontrado** - 🔴 [2 pontos | Peso: 0.2]
-   **`404 Not Found` → Não encontrado** - 🔴 [2 pontos | Peso: 0.2]

------------------------------------------------------------------------

### PUT /courses/{id} - 🔴 **[6 pontos | Peso: 0.6]**

-   **`200 OK` → Atualizado** - 🔴 [2 pontos | Peso: 0.2]
-   **`400 Bad Request` → Dados inválidos** - 🔴 [2 pontos | Peso: 0.2]
-   **`404 Not Found` → Curso não existe** - 🔴 [2 pontos | Peso: 0.2]

------------------------------------------------------------------------

### DELETE /courses/{id} - 🔴 **[5 pontos | Peso: 0.5]**

-   **`204 No Content` → Removido** - 🔴 [3 pontos | Peso: 0.3]
-   **`404 Not Found` → Não existe** - 🔴 [2 pontos | Peso: 0.2]

------------------------------------------------------------------------

# 🗂️ Persistência - 🔴 **[10 pontos | Peso: 1.0]**

Você deve escolher **UMA** das opções:

-   **Banco em memória (H2)** - 🔴 [10 pontos | Peso: 1.0]

**OU**

-   **Banco de dados real (PostgreSQL ou MySQL)** - 🔴 [10 pontos | Peso: 1.0]

------------------------------------------------------------------------

# 🧪 O que será Avaliado - 🔴 **[5 pontos | Peso: 0.5]**

-   **Uso correto de anotações REST** - 🔴 [1 ponto | Peso: 0.1]
-   **Uso correto de códigos HTTP** - 🔴 [1 ponto | Peso: 0.1]
-   **Retorno correto do header `Content-Type`** - 🔴 [1 ponto | Peso: 0.1]
-   **Uso de Bean Validation** - 🔴 [1 ponto | Peso: 0.1]
-   **Organização do código (Resource, DTO, Service, Repository, DAO)** - 🟢 [3 pontos | Peso: 0.3] (Plus)

------------------------------------------------------------------------

# 🌟 Diferencial (Opcional) - 🟢 **[18 pontos extras | Peso: 1.8]**

Pontos extras se implementar:

-   **Testes com `@QuarkusTest`** - 🟢 [4 pontos | Peso: 0.4]
-   **Tratamento global de exceção** - 🟢 [4 pontos | Peso: 0.4]
-   **Paginação em GET /courses** - 🟢 [3 pontos | Peso: 0.3]
-   **Uso de DTOs** - 🟢 [4 pontos | Peso: 0.4]
-   **Health Check** - 🟢 [3 pontos | Peso: 0.3]

------------------------------------------------------------------------

# 📋 Resumo da Pontuação

## Pontos Obrigatórios (Total: 100 pontos)
- 📦 Modelo de Dados: 15 pontos
- 🔧 Requisitos Funcionais (CRUD): 25 pontos
- 📡 Endpoints: 15 pontos
- 🧾 Validações: 15 pontos
- 🌐 Requisitos HTTP: 30 pontos
- 🗂️ Persistência: 10 pontos
- 🧪 Avaliação Geral: 5 pontos

## Pontos Opcionais (Total: 30 pontos extras)
- 📡 Endpoints de Lessons: 6 pontos (3 + 3)
- 🌐 Header Location no POST: 3 pontos
- 🧪 Organização avançada do código: 3 pontos
- 🌟 Diferenciais: 18 pontos
  - Testes com @QuarkusTest: 4 pontos
  - Tratamento global de exceção: 4 pontos
  - Paginação em GET /courses: 3 pontos
  - Uso de DTOs: 4 pontos
  - Health Check: 3 pontos
