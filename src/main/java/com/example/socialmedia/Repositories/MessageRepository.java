package com.example.socialmedia.Repositories;

import com.example.socialmedia.Entities.Message;
import com.example.socialmedia.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiverOrderBySentAtAsc(User receiver);
}
