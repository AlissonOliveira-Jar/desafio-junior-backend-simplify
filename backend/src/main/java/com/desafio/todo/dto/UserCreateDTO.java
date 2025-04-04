package com.desafio.todo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record UserCreateDTO(
        @NotBlank(message = "Nome obrigatório")
        String name,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email obrigatório")
        String email,

        @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Senha deve conter: letras maiúsculas, minúsculas, números e caracteres especiais"
        )
        String password,

        Set<String> roles
) {}