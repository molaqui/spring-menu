package com.example.demo.DAO;

import com.example.demo.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserId(Long userId);
    // Méthode pour compter les messages par userId
    long countByUserId(Long userId);
}
