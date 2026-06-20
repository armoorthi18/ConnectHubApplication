package com.example.connecthubapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStats {

    private long totalUsers;

    private long totalPosts;

    private long totalAchievements;
}
