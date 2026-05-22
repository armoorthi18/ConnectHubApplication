package com.example.connecthubapplication.repository;

import com.example.connecthubapplication.entity.Achievement;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AchievementRepository
        extends MongoRepository<Achievement, String> {

    List<Achievement> findByUserEmail(
            String userEmail
    );
}