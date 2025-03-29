package com.desafio.todo.service;

import com.desafio.todo.entity.Todo;
import com.desafio.todo.repository.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> create(Todo todo) {
        todoRepository.save(todo);
        return list();
    }

    public List<Todo> list() {
        Sort sort = Sort.by("prioridade").descending().and(Sort.by("nome").ascending());
        return todoRepository.findAll(sort);
    }

    public List<Todo> update(Todo todo) {
        if (todo.getID() == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo para atualização.");
        }
        if (!todoRepository.existsById(todo.getID())) {
            return new ArrayList<>();
        }
        todoRepository.save(todo);
        return list();
    }

    public List<Todo> delete(UUID ID) {
        todoRepository.deleteById(ID);
        return list();
    }
}
