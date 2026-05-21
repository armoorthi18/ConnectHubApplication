package com.example.connecthubapplication.controller;
;

import com.example.connecthubapplication.dto.RegisterRequest;
import com.example.connecthubapplication.entity.User;
import com.example.connecthubapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    @PostMapping("/create-user")
    public String createUser(
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

        user.setRole("EMPLOYEE");

        userRepository.save(user);

        return "Employee Created Successfully";
    }
}