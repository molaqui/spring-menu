package com.example.demo.DAO;

// FoodRepository.java
import com.example.demo.Entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByUserId(Long userId);
    List<Food> findAllByCategoryNameAndUserId(String categoryName, Long userId);
    Optional<Food> findByIdAndUserId(Long id, Long userId); // Add this method if it doesn't exist
    long countByUserId(Long userId);

    void deleteByCategoryId(Long categoryId);
}
