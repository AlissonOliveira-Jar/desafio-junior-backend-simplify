# To-do List

## Descrição do Projeto

Este projeto é uma API RESTful para gerenciar uma lista de tarefas ("to-dos") associadas a usuários. Ele permite criar, listar, atualizar e excluir tarefas, além de oferecer funcionalidades de cadastro e login de usuários com autenticação baseada em JWT (JSON Web Tokens).

## Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias:

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.4.4
* **Segurança:** Spring Security com autenticação JWT
* **Biblioteca JWT:** Nimbus JOSE + JWT
* **Criptografia de Senhas:** BCrypt
* **Gerenciador de Dependências:** Maven
* **Banco de Dados:** PostgreSQL
* **Gerenciamento do Banco de Dados:** Docker Compose
* **API Documentation:** SpringDoc OpenAPI (Swagger UI)

## Pré-requisitos

Antes de executar o projeto, você precisará ter as seguintes ferramentas instaladas:

* **Java Development Kit (JDK):** Versão 21 (ou uma versão compatível com o Spring Boot 3).
* **Maven:** Para gerenciar as dependências e construir o projeto.
* **Docker:** Necessários para executar o banco de dados PostgreSQL.
* **Uma ferramenta para enviar requisições HTTP:** Postman, Insomnia, `curl`, etc. (recomendado para testar os endpoints de autenticação e os protegidos).

## Como Executar a Aplicação

Existem duas maneiras principais de executar a aplicação: utilizando o Spring Boot com Docker Compose (recomendado para configurar o banco de dados) ou executando o Spring Boot diretamente (requer uma instância do PostgreSQL em execução).

### Utilizando Spring Boot com Docker Compose (Recomendado)

Esta é a maneira mais fácil de executar a aplicação, pois o Spring Boot cuidará de iniciar o banco de dados PostgreSQL definido no seu arquivo `docker-compose.yml`.

1.  **Clone o repositório do projeto:**
    ```bash
    git clone git@github.com:AlissonOliveira-Jar/desafio-junior-backend-simplify.git
    cd todo
    ```

2.  **Certifique-se de que o Docker esteja em execução na sua máquina.**
    * **No Windows:** O Docker Desktop precisa estar rodando.
    * **No macOS e Linux:** O Docker Engine precisa estar em execução.

3.  **Construa o projeto Maven:**
    ```bash
    mvn clean install
    ```

4.  **Execute a aplicação Spring Boot:**
    Você pode executar a aplicação de duas maneiras:

    * **Através do Maven:**
        ```bash
        mvn spring-boot:run
        ```
    * **Executando o JAR gerado:**
      Após o `mvn clean install`, um arquivo JAR será criado no diretório `target`. Você pode executá-lo com o seguinte comando:
        ```bash
        java -jar target/todo-0.0.1-SNAPSHOT.jar
        ```
      (Substitua `todo-0.0.1-SNAPSHOT.jar` pelo nome exato do seu arquivo JAR).

    O Spring Boot automaticamente detectará o arquivo `docker-compose.yml` (geralmente na raiz do projeto) e iniciará o serviço do PostgreSQL antes de iniciar a sua aplicação.

### Executando o Spring Boot Diretamente (Requer PostgreSQL Instalado)

1.  **Certifique-se de ter o PostgreSQL instalado e configurado em sua máquina.**
2.  **Configure as propriedades de conexão com o banco de dados no arquivo `src/main/resources/application.properties` ou `application.yml`.** Você precisará fornecer as informações de host, porta, nome do banco de dados, usuário e senha do seu PostgreSQL.
3.  **Clone o repositório do projeto:**
    ```bash
    git clone git@github.com:AlissonOliveira-Jar/desafio-junior-backend-simplify.git
    cd todo
    ```

4.  **Construa o projeto Maven:**
    ```bash
    mvn clean install
    ```

5.  **Execute a aplicação Spring Boot** (como descrito no passo 4 da seção anterior).

## Endpoints da API

A API fornece os seguintes endpoints para gerenciar usuários e tarefas:

### Endpoints de Autenticação

* **`POST /users`:** Cria um novo usuário.
    * **Corpo da Requisição (Exemplo):**
        ```json
        {
          "name": "Nome do Usuário",
          "email": "emaildousuario@example.com",
          "password": "senha123"
        }
        ```
    * **Resposta:** Retorna os detalhes do usuário criado com status `201 Created`.

* **`POST /auth/login`:** Realiza o login de um usuário e gera um token JWT.
    * **Corpo da Requisição (Exemplo):**
        ```json
        {
          "email": "emaildousuario@example.com",
          "password": "senha123"
        }
        ```
    * **Resposta:** Retorna o token JWT no corpo da resposta com status `200 OK`. Este token deve ser usado para acessar os endpoints protegidos.

### Endpoints de Tarefas (Protegidos com Autenticação JWT)

Para acessar os endpoints abaixo, você precisa incluir o token JWT recebido no login no header `Authorization` da sua requisição, utilizando o esquema **Bearer Token**. Exemplo: `Authorization: Bearer SEU_TOKEN_JWT`.

* **`POST /todos`:** Cria uma nova tarefa para o usuário autenticado.
    * **Corpo da Requisição (Exemplo):**
        ```json
        {
          "nome": "Comprar pão",
          "descricao": "Ir à padaria comprar pão francês",
          "realizado": false,
          "prioridade": 1
        }
        ```
    * **Resposta:** Retorna os detalhes da tarefa criada com status `201 Created`.

* **`GET /todos`:** Lista todas as tarefas do usuário autenticado.
    * **Resposta:** Retorna um array JSON contendo as tarefas do usuário com status `200 OK`.

* **`PUT /todos/{id}`:** Atualiza uma tarefa existente do usuário autenticado. Espera o `id` da tarefa a ser atualizada na URL e um objeto JSON `Todo` no corpo da requisição.
    * **Corpo da Requisição (Exemplo):**
        ```json
        {
          "nome": "Comprar pão integral",
          "descricao": "Ir ao supermercado comprar pão integral",
          "realizado": true,
          "prioridade": 2
        }
        ```
    * **Resposta:** Retorna os detalhes da tarefa atualizada com status `200 OK`. Retorna `404 Not Found` se a tarefa com o `id` especificado não for encontrada ou não pertencer ao usuário autenticado.

* **`DELETE /todos/{id}`:** Exclui uma tarefa existente do usuário autenticado. Espera o `id` da tarefa a ser excluída na URL.
    * **Resposta:** Retorna status `204 No Content` em caso de sucesso. Retorna `404 Not Found` se a tarefa com o `id` especificado não for encontrada ou não pertencer ao usuário autenticado.

### Endpoint de Usuário (Protegido com Autenticação JWT)

* **`GET /users/{id}`:** Retorna os detalhes do usuário autenticado. O `{id}` na URL deve ser o ID do usuário autenticado (extraído do token JWT).
    * **Resposta:** Retorna os detalhes do usuário com status `200 OK`. Retorna `403 Forbidden` se tentar acessar informações de outro usuário.

## Documentação da API (Swagger UI)

A documentação completa da API, incluindo todos os endpoints, parâmetros e modelos de dados, pode ser acessada através do Swagger UI. Após executar a aplicação, acesse o seguinte endereço no seu navegador:

http://localhost:8080/swagger-ui.html

(Assumindo que a sua aplicação está rodando na porta 8080. Verifique os logs da aplicação para a porta correta se necessário).

## Próximos Passos (Opcional)

* Implementar testes unitários e de integração.
* Adicionar tratamento de erros mais detalhado.
* Implementar paginação para a listagem de tarefas.
* Adicionar mais campos à entidade `Todo` (data de criação, data de conclusão, etc.).
* Implementar autorização baseada em roles (se necessário).