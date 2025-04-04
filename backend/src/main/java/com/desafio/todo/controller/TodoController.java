package com.desafio.todo.controller;

import com.desafio.todo.dto.TodoDTO;
import com.desafio.todo.entity.User;
import com.desafio.todo.repository.UserRepository;
import com.desafio.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TodoDTO> create(@RequestBody TodoDTO dto, @AuthenticationPrincipal User user) {
        TodoDTO created = todoService.create(dto, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<TodoDTO> list(@AuthenticationPrincipal User user) {
        return todoService.list(user.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> update(@PathVariable UUID id, @RequestBody TodoDTO dto, @AuthenticationPrincipal User user) {
        TodoDTO updated = todoService.update(id, dto, user.getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        todoService.delete(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
