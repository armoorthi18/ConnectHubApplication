package com.example.connecthubapplication.controller;

import com.example.connecthubapplication.entity.Achievement;
import com.example.connecthubapplication.entity.User;

import com.example.connecthubapplication.repository.AchievementRepository;
import com.example.connecthubapplication.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;

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
    // UPDATE STATUS

    @PutMapping("/status")
    public User updateStatus(

            @RequestBody
            Map<String, String> body,

            Authentication authentication
    ) {

        // Logged-in User Email

        String email =
                authentication.getName();

        // Find User

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        // Update Status

        user.setStatusMessage(
                body.get("statusMessage")
        );

        // Save to MongoDB
        return userRepository.save(user);
    }

    @GetMapping("/achievements")
    public List<Achievement> getAchievements(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        return achievementRepository.findByUserEmail(
                email
        );
    }

    @PostMapping("/achievement")
    public Achievement addAchievement(

            @RequestBody
            Map<String, String> body,

            Authentication authentication
    ) {

        String email =
                authentication.getName();

        Achievement achievement =
                new Achievement();

        achievement.setUserEmail(email);

        achievement.setTitle(
                body.get("title")
        );

        achievement.setDescription(
                body.get("description")
        );

        achievement.setCreatedAt(
                LocalDateTime.now()
        );

        return achievementRepository.save(
                achievement
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