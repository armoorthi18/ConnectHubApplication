package com.example.connecthubapplication.repository;

import com.example.connecthubapplication.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository
        extends MongoRepository<Message, String> {

    @Query("""
        {
          "$or": [
            {
              "senderEmail": ?0,
              "receiverEmail": ?1
            },
            {
              "senderEmail": ?1,
              "receiverEmail": ?0
            }
          ]
        }
        """)
    List<Message> findConversation(
            String email1,
            String email2);
    List<Message> findByReceiverEmailOrderBySentAtDesc(
            String receiverEmail);
}
