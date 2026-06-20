package com.example.connecthubapplication.controller;
;

import com.example.connecthubapplication.dto.AdminStats;
import com.example.connecthubapplication.dto.RegisterRequest;
import com.example.connecthubapplication.entity.User;
import com.example.connecthubapplication.repository.AchievementRepository;
import com.example.connecthubapplication.repository.PostRepository;
import com.example.connecthubapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final AchievementRepository achievementRepository;

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

    @GetMapping("/stats")
    public AdminStats getStatistics(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Admin access required"
            );
        }

        return new AdminStats(
                userRepository.count(),
                postRepository.count(),
                achievementRepository.count()
        );
    }
}