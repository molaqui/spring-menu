package com.example.demo.DAO;

import com.example.demo.Entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    // Additional query methods if needed
}