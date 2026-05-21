package com.example.connecthubapplication.controller;

import com.example.connecthubapplication.dto.LoginRequest;
import com.example.connecthubapplication.dto.RegisterRequest;
import com.example.connecthubapplication.entity.User;
import com.example.connecthubapplication.repository.UserRepository;
import com.example.connecthubapplication.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    // USER SIGNUP
    @PostMapping("/signup")
    public String signup(
            @RequestBody RegisterRequest request
    ) {

        if (userRepository.findByEmail(
                request.getEmail()).isPresent()) {

            return "Email already exists";
        }

        User user = new User();

        user.setFullName(request.getFullName());

        user.setEmail(request.getEmail());

        user.setPassword(
                encoder.encode(request.getPassword())
        );

        // Default Role
        user.setRole("EMPLOYEE");

        userRepository.save(user);

        return "Signup Successful";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request
    ) {

        User user = userRepository.findByEmail(
                request.getEmail()
        ).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        boolean matches = encoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!matches) {

            return "Invalid Password";
        }

        return jwtUtil.generateToken(
                user.getEmail()
        );
    }
}