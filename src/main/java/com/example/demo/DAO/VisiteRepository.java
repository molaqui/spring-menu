package com.example.demo.DAO;

import com.example.demo.Entity.Visite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisiteRepository extends JpaRepository<Visite, Long> {
    Optional<Visite> findByUserId(Long userId);
}
