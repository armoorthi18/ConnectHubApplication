package com.example.connecthubapplication.service;

import com.example.connecthubapplication.entity.Message;
import com.example.connecthubapplication.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message sendMessage(Message message) {

        message.setSentAt(LocalDateTime.now());

        return messageRepository.save(message);
    }


}
