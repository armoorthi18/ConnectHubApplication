package com.example.connecthubapplication.controller;

import com.example.connecthubapplication.dto.MessageRequest;
import com.example.connecthubapplication.entity.Message;
import com.example.connecthubapplication.entity.User;
import com.example.connecthubapplication.repository.MessageRepository;
import com.example.connecthubapplication.repository.UserRepository;
import com.example.connecthubapplication.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MessageController {

    private final MessageService messageService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @PostMapping("/send")
    @ResponseBody
    public Message sendMessage(
            @RequestBody MessageRequest request,
            Authentication authentication) {

        String senderEmail = authentication.getName();

        User sender = userRepository
                .findByEmail(senderEmail)
                .orElseThrow();

        Message message = new Message();

        message.setSenderEmail(senderEmail);
        message.setSenderName(sender.getFullName());

        message.setReceiverEmail(
                request.getReceiverEmail());

        message.setContent(
                request.getContent());

        message.setSentAt(
                LocalDateTime.now());

        return messageService.sendMessage(message);
    }

    @GetMapping("/conversation/{email}")
    @ResponseBody
    public List<Message> getConversation(
            @PathVariable String email,
            Authentication authentication) {

        String currentUserEmail =
                authentication.getName();

        return messageRepository.findConversation(
                currentUserEmail,
                email);
    }

    @GetMapping("/received")
    @ResponseBody
    public List<Message> getReceivedMessages(
            Authentication authentication) {

        String email = authentication.getName();

        return messageRepository
                .findByReceiverEmailOrderBySentAtDesc(email);
    }
}
