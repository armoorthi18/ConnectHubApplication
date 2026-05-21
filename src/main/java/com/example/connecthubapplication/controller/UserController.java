package com.example.connecthubapplication.controller;

import com.example.connecthubapplication.entity.User;

import com.example.connecthubapplication.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/profile")
    public User getProfile(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );
    }
    @PutMapping("/update-profile")
    public User updateProfile(
            @RequestBody User updatedUser,
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow();

        user.setFullName(
                updatedUser.getFullName()
        );

        user.setDepartment(
                updatedUser.getDepartment()
        );

        user.setPosition(
                updatedUser.getPosition()
        );

        user.setStatusMessage(
                updatedUser.getStatusMessage()
        );

        user.setProfilePicture(
                updatedUser.getProfilePicture()
        );

        user.setInterests(
                updatedUser.getInterests()
        );

        return userRepository.save(user);
    }
}