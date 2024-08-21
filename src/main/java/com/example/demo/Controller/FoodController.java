package com.example.demo.Controller;

import com.example.demo.Entity.Category;
import com.example.demo.Entity.Food;
import com.example.demo.Entity.FoodImage;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{userId}")
    public List<Food> getAllFoods(@PathVariable Long userId) {
        return foodService.getAllFoods(userId);
    }

    @PostMapping
    public ResponseEntity<String> addFood(
            @RequestParam("images") MultipartFile[] files,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("userId") Long userId) {
        try {
            Category category = categoryService.findByName(categoryName, userId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            Food food = new Food();
            food.setName(name);
            food.setPrice(price);
            food.setDescription(description);
            food.setCategory(category);

            List<FoodImage> images = new ArrayList<>();
            for (MultipartFile file : files) {
                FoodImage image = new FoodImage();
                image.setImage(file.getBytes());
                image.setFood(food);
                images.add(image);
            }
            food.setImages(images);

            foodService.saveFood(food, userId);
            return ResponseEntity.ok("Food added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding food: " + e.getMessage());
        }
    }

    @GetMapping("/by-category/{categoryName}/{userId}")
    public ResponseEntity<List<Food>> getFoodsByCategory(@PathVariable String categoryName, @PathVariable Long userId) {
        try {
            List<Food> foods = foodService.findByCategoryName(categoryName, userId);
            return ResponseEntity.ok(foods);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ArrayList<>());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFood(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("userId") Long userId) {
        try {
            Category category = categoryService.findByName(categoryName, userId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            Food food = foodService.findById(id, userId)
                    .orElseThrow(() -> new RuntimeException("Food not found"));
            food.setName(name);
            food.setPrice(price);
            food.setDescription(description);
            food.setCategory(category);
            foodService.saveFood(food, userId);
            return ResponseEntity.ok("Food updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating food: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id, @PathVariable Long userId) {
        try {
            foodService.deleteFood(id, userId);
            return ResponseEntity.ok("Food deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting food: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/{userId}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id, @PathVariable Long userId) {
        try {
            Optional<Food> food = foodService.findById(id, userId);
            if (food.isPresent()) {
                return ResponseEntity.ok(food.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> countFoodsByUserId(@PathVariable Long userId) {
        try {
            long count = foodService.countFoodsByUserId(userId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/{id}/add-image")
    public ResponseEntity<String> addImageToFood(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile file,
            @RequestParam("userId") Long userId) {
        try {
            Food food = foodService.findById(id, userId)
                    .orElseThrow(() -> new RuntimeException("Food not found"));

            FoodImage image = new FoodImage();
            image.setImage(file.getBytes());
            image.setFood(food);

            food.getImages().add(image);
            foodService.saveFood(food, userId);

            return ResponseEntity.ok("Image added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding image: " + e.getMessage());
        }
    }
}
