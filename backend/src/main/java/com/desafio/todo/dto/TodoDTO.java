package com.desafio.todo.dto;

import com.desafio.todo.entity.Todo;
import com.desafio.todo.entity.User;

import java.util.UUID;

public record TodoDTO(UUID ID, String nome, String descricao, boolean realizado, int prioridade, UUID userId) {

    public static TodoDTO fromEntity(Todo todo) {
        return new TodoDTO(
                todo.getID(),
                todo.getNome(),
                todo.getDescricao(),
                todo.isRealizado(),
                todo.getPrioridade(),
                todo.getUser().getId()
        );
    }

    public Todo toEntity(User user) {
        Todo todo = new Todo();
        todo.setNome(this.nome);
        todo.setDescricao(this.descricao);
        todo.setRealizado(this.realizado);
        todo.setPrioridade(this.prioridade);
        todo.setUser(user);
        return todo;
    }
}
