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

    public List<Todo> update(UUID ID, Todo todo) {
        Optional<Todo> existingTodoOptional = todoRepository.findById(ID);

        if (existingTodoOptional.isPresent()) {
            Todo existingTodo = existingTodoOptional.get();
            existingTodo.setNome(todo.getNome());
            existingTodo.setDescricao(todo.getDescricao());
            existingTodo.setRealizado(todo.isRealizado());
            existingTodo.setPrioridade(todo.getPrioridade());
            todoRepository.save(existingTodo);
            return list();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Todo> delete(UUID ID) {
        if (!todoRepository.existsById(ID)) {
            return null;
        }
        todoRepository.deleteById(ID);
        return list();
    }
}
