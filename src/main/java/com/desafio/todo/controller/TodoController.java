package com.desafio.todo.controller;

import com.desafio.todo.entity.Todo;
import com.desafio.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    ResponseEntity<List<Todo>> create(@RequestBody Todo todo) {
        List<Todo> todos = todoService.create(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(todos);
    }

    @GetMapping
    List<Todo> list(Todo todo) {
        return todoService.list();
    }

    @PutMapping
    List<Todo> update(@RequestBody Todo todo) {
        return todoService.update(todo);
    }

    @DeleteMapping("{id}")
    List<Todo> delete(@PathVariable("id") UUID ID) {
        return todoService.delete(ID);
    }
}
