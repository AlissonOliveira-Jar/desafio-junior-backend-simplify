package com.desafio.todo.dto;

import com.desafio.todo.entity.Todo;

import java.util.UUID;

public record TodoDTO(UUID ID, String nome, String descricao, boolean realizado, int prioridade) {
    public static TodoDTO fromEntity(Todo todo) {
        return new TodoDTO(
                todo.getID(),
                todo.getNome(),
                todo.getDescricao(),
                todo.isRealizado(),
                todo.getPrioridade()
        );
    }
}
