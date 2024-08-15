package com.example.demo.DAO;

// CategoryRepository.java
import com.example.demo.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
    public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserId(Long userId);
    Optional<Category> findByNameAndUserId(String name, Long userId);
    long countByUserId(Long userId);
    }
