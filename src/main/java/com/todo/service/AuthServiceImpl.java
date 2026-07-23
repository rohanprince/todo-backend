package com.todo.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.dto.AuthResponse;
import com.todo.dto.LoginRequest;
import com.todo.dto.RegisterRequest;
import com.todo.dto.UserInfo;
import com.todo.entity.User;
import com.todo.exception.ResourceNotFoundException;
import com.todo.exception.UnauthorizedException;
import com.todo.exception.UserAlreadyExistsException;
import com.todo.repository.UserRepository;
import com.todo.security.JwtService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getEmail());

        UserInfo userInfo = new UserInfo(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );

        return new AuthResponse(
                HttpStatus.CREATED.value(),
                "User Registered Successfully",
                token,
                userInfo
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid Password");
        }

        String token = jwtService.generateToken(user.getEmail());

        UserInfo userInfo = new UserInfo(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

        return new AuthResponse(
                HttpStatus.OK.value(),
                "Login Successful",
                token,
                userInfo
        );
    }
}