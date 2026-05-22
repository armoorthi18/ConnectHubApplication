package com.example.connecthubapplication.entity;
import lombok.Data;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "achievements")

@Data
public class Achievement {

    @Id
    private String id;

    private String userEmail;

    private String title;

    private String description;

    private LocalDateTime createdAt;
}
