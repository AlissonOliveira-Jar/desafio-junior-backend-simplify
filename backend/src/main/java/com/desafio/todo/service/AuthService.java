package com.desafio.todo.service;

import com.desafio.todo.dto.LoginDTO;
import com.desafio.todo.entity.User;
import com.desafio.todo.repository.UserRepository;
import com.desafio.todo.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(LoginDTO dto) {
        User user = userRepository.findByEmail(dto.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        return jwtService.generateToken(user);
    }

}
