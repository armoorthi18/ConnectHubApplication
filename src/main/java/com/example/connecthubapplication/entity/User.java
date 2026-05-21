package com.example.connecthubapplication.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    private String id;

    private String fullName;

    private String email;

    private String password;

    private String role;

    private String department;

    private String position;

    private String statusMessage;

    private List<String> interests;

    private String profilePicture;
}