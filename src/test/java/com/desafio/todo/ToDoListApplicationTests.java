package com.desafio.todo;

import com.desafio.todo.entity.Todo;
import com.desafio.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodolistApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private TodoRepository todoRepository;

	@BeforeEach
	void setUp() {
		todoRepository.deleteAll();
	}

	@Test
	void testCreateTodoSuccess() {
		var todo = new Todo("todo 1", "desc todo 1", false, 1);

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(todo)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(1)
				.jsonPath("$[0].nome").isEqualTo(todo.getNome())
				.jsonPath("$[0].descricao").isEqualTo(todo.getDescricao())
				.jsonPath("$[0].realizado").isEqualTo(todo.isRealizado())
				.jsonPath("$[0].prioridade").isEqualTo(todo.getPrioridade());
	}

	@Test
	public void testCreateTodoFailure() {
		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(new Todo("", "", false, 0))
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$.nome").isEqualTo("O nome não pode estar vazio.")
				.jsonPath("$.descricao").isEqualTo("A descrição não pode estar vazia.")
				.jsonPath("$.prioridade").isEqualTo("A prioridade deve ser no mínimo 1.");
	}

	@Test
	void testListTodosSuccess() {
		var todo1 = new Todo("todo 1", "desc todo 1", false, 1);
		var todo2 = new Todo("todo 2", "desc todo 2", true, 2);

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(todo1)
				.exchange();

		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(todo2)
				.exchange();

		webTestClient
				.get()
				.uri("/todos")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(2)
				.jsonPath("$[0].nome").isEqualTo(todo2.getNome())
				.jsonPath("$[1].nome").isEqualTo(todo1.getNome());
	}

	@Test
	void testListTodosEmpty() {
		webTestClient
				.get()
				.uri("/todos")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(0);
	}

	@Test
	void testUpdateTodoSuccess() {
		var todoInicial = new Todo("todo inicial", "desc inicial", false, 1);
		var createdTodoList = webTestClient
				.post()
				.uri("/todos")
				.bodyValue(todoInicial)
				.exchange()
				.expectStatus().isCreated()
				.expectBodyList(Todo.class)
				.returnResult()
				.getResponseBody();

		assert createdTodoList != null && !createdTodoList.isEmpty();
		var createdTodo = createdTodoList.getFirst();

		var todoAtualizado = new Todo("todo atualizado", "nova descrição", true, 2);

		assert createdTodo != null;
		webTestClient
				.put()
				.uri("/todos/" + createdTodo.getID())
				.bodyValue(todoAtualizado)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$[?(@.id=='" + createdTodo.getID().toString() + "')].nome").isEqualTo(todoAtualizado.getNome())
				.jsonPath("$[?(@.id=='" + createdTodo.getID().toString() + "')].descricao").isEqualTo(todoAtualizado.getDescricao())
				.jsonPath("$[?(@.id=='" + createdTodo.getID().toString() + "')].realizado").isEqualTo(todoAtualizado.isRealizado())
				.jsonPath("$[?(@.id=='" + createdTodo.getID().toString() + "')].prioridade").isEqualTo(todoAtualizado.getPrioridade());
	}
}

