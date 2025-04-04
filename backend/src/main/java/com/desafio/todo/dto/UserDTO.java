package com.desafio.todo.dto;

import com.desafio.todo.entity.User;

import java.util.List;
import java.util.UUID;

public record UserDTO(UUID id, String name, String email, List<TodoDTO> todos) {
    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getTodos().stream()
                        .map(TodoDTO::fromEntity)
                        .toList()
        );
    }
}
