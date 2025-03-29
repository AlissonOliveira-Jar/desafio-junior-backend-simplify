# To-do List

## Descrição do Projeto

Este projeto é uma API RESTful simples para gerenciar uma lista de tarefas ("to-dos"). Ele permite criar, listar, atualizar e excluir tarefas, com funcionalidades como nome, descrição, status de realização e prioridade.

## Tecnologias Utilizadas

Este projeto foi construído utilizando as seguintes tecnologias:

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.4.4
* **Gerenciador de Dependências:** Maven
* **Banco de Dados:** PostgreSQL
* **Gerenciamento do Banco de Dados:** Docker Compose
* **API Documentation:** SpringDoc OpenAPI (Swagger UI)

## Pré-requisitos

Antes de executar o projeto, você precisará ter as seguintes ferramentas instaladas:

* **Java Development Kit (JDK):** Versão 21 (ou uma versão compatível com o Spring Boot 3).
* **Maven:** Para gerenciar as dependências e construir o projeto.
* **Docker:** Necessários para executar o banco de dados PostgreSQL.

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
  * **No Windows:** O Docker Desktop precisa estar rodando (isso pode incluir ter o Docker Hub aberto, dependendo da sua configuração).
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

A API fornece os seguintes endpoints para gerenciar as tarefas:

* **`POST /todos`:** Cria uma tarefa. Espera um objeto JSON `Todo` no corpo da requisição.
  * **Corpo da Requisição (Exemplo):**
      ```json
      {
        "nome": "Comprar pão",
        "descricao": "Ir à padaria comprar pão francês",
        "realizado": false,
        "prioridade": 1
      }
      ```
  * **Resposta:** Retorna um array JSON contendo todos os `Todos` atualizados (incluindo o recém-criado) com status `201 Created`.

* **`GET /todos`:** Lista todas as tarefas.
  * **Resposta:** Retorna um array JSON contendo todas as tarefas com status `200 OK`.

* **`PUT /todos/{id}`:** Atualiza uma tarefa existente. Espera um objeto JSON `Todo` no corpo da requisição e o `id` da tarefa a ser atualizada na URL.
  * **Corpo da Requisição (Exemplo):**
      ```json
      {
        "nome": "Comprar pão",
        "descricao": "Ir à padaria comprar pão integral",
        "realizado": true,
        "prioridade": 2
      }
      ```
  * **Resposta:** Retorna um array JSON contendo todos os `Todos` atualizados com status `200 OK`. Retorna `404 Not Found` se a tarefa com o `id` especificado não for encontrada.

* **`DELETE /todos/{id}`:** Exclui uma tarefa existente. Espera o `id` da tarefa a ser excluída na URL.
  * **Resposta:** Retorna um array JSON contendo todos os `Todos` restantes com status `200 OK`. Retorna `404 Not Found` se a tarefa com o `id` especificado não for encontrada.

## Documentação da API (Swagger UI)

A documentação completa da API, incluindo todos os endpoints, parâmetros e modelos de dados, pode ser acessada através do Swagger UI. Após executar a aplicação, acesse o seguinte endereço no seu navegador:

http://localhost:8080/swagger-ui.html

(Assumindo que a sua aplicação está rodando na porta 8080. Verifique os logs da aplicação para a porta correta se necessário).
