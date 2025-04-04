package com.desafio.todo.service;

import com.desafio.todo.dto.TodoDTO;
import com.desafio.todo.entity.Todo;
import com.desafio.todo.entity.User;
import com.desafio.todo.repository.TodoRepository;
import com.desafio.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    public TodoDTO create(TodoDTO dto, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Todo todo = dto.toEntity(user);
        Todo savedTodo = todoRepository.save(todo);
        return TodoDTO.fromEntity(savedTodo);
    }

    public List<TodoDTO> list(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Sort sort = Sort.by(Sort.Direction.DESC, "prioridade").and(Sort.by(Sort.Direction.ASC, "nome"));
        List<Todo> todos = todoRepository.findByUser(user, sort);
        return todos.stream().map(TodoDTO::fromEntity).toList();
    }

    public TodoDTO update(UUID todoId, TodoDTO dto, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Todo existingTodo = todoRepository.findByIdWithUser(todoId).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        if (!existingTodo.getUser().getId().equals(userId)) {
            throw new RuntimeException("Acesso negado");
        }

        existingTodo.setNome(dto.nome());
        existingTodo.setDescricao(dto.descricao());
        existingTodo.setRealizado(dto.realizado());
        existingTodo.setPrioridade(dto.prioridade());

        Todo updatedTodo = todoRepository.save(existingTodo);
        return TodoDTO.fromEntity(updatedTodo);
    }

    public void delete(UUID todoId, UUID userId) {
        Todo existingTodo = todoRepository.findByIdWithUser(todoId).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        if (!existingTodo.getUser().getId().equals(userId)) {
            throw new RuntimeException("Acesso negado");
        }

        todoRepository.delete(existingTodo);
    }
}
