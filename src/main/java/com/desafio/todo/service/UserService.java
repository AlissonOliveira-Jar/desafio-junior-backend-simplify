package com.desafio.todo.service;

import com.desafio.todo.dto.UserCreateDTO;
import com.desafio.todo.dto.UserDTO;
import com.desafio.todo.entity.User;
import com.desafio.todo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(UserCreateDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email já cadastrado");
        }

        User user = new User(dto.name(), dto.email().toLowerCase().trim(), dto.roles());
        user.setPassword(passwordEncoder.encode(dto.password()));

        User savedUser = userRepository.save(user);
        return UserDTO.fromEntity(savedUser);
    }

    public UserDTO getUserById(UUID id) {
        User user = userRepository.findByIdWithTodos(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return UserDTO.fromEntity(user);
    }
}