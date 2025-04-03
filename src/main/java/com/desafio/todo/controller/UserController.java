package com.desafio.todo.controller;

import com.desafio.todo.dto.UserCreateDTO;
import com.desafio.todo.dto.UserDTO;
import com.desafio.todo.entity.User;
import com.desafio.todo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO dto) {
        UserDTO user = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id, @AuthenticationPrincipal User authenticatedUser) {
        if (!authenticatedUser.getId().equals(id)) {
            throw new RuntimeException("Acesso negado");
        }
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}