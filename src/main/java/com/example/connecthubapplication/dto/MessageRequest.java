package com.example.connecthubapplication.dto;

import lombok.Data;

@Data
public class MessageRequest {

    private String receiverEmail;

    private String content;
}
