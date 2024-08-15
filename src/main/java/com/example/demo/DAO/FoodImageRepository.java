package com.example.demo.DAO;

import com.example.demo.Entity.Food;
import com.example.demo.Entity.FoodImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {
    void deleteAllByFood(Food food);
}
