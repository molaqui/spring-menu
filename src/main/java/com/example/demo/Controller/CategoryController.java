package com.example.demo.Controller;

import com.example.demo.DAO.CategoryRepository;
import com.example.demo.DTO.CategoryDTO;
import com.example.demo.Entete.AddCategoriesRequest;
import com.example.demo.Entity.Category;
import com.example.demo.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/{userId}")
    public List<CategoryDTO> getAllCategories(@PathVariable Long userId) {
        return categoryService.getAllCategories(userId).stream()
                .map(category -> {
                    CategoryDTO newCategory = new CategoryDTO();
                    newCategory.setId(category.getId());
                    newCategory.setName(category.getName());
                    newCategory.setImage(Base64.getEncoder().encodeToString(category.getImage()));
                    return newCategory;
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("name") String name, @RequestParam("userId") Long userId) {
        try {
            Category category = new Category();
            category.setName(name);
            category.setImage(file.getBytes());
            categoryService.saveCategory(category, userId);
            return ResponseEntity.ok("Catégorie et image ajoutées avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'upload de l'image : " + e.getMessage());
        }
    }

    @GetMapping("/names/{userId}")
    public List<String> getAllCategoryNames(@PathVariable Long userId) {
        return categoryService.getAllCategories(userId).stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }
    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> getCategoryCountByUserId(@PathVariable Long userId) {
        try {
            long count = categoryService.countCategoriesByUserId(userId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(0L);
        }
    }

    // Endpoint for adding selected predefined categories to the chef's menu
    @PostMapping("/add-predefined")
    public ResponseEntity<String> addPredefinedCategoriesToUser(@RequestBody AddCategoriesRequest request) {
        try {
            categoryService.addSelectedCategoriesToUser(request.getUserId(), request.getCategoryIds(), request.getFoodIds());
            return ResponseEntity.ok("Categories and associated foods added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding categories: " + e.getMessage());
        }
    }
    // Endpoint to get predefined categories associated with user id 26
    @GetMapping("/predefined")
    public ResponseEntity<List<Category>> getPredefinedCategories() {
        try {
            List<Category> predefinedCategories = categoryService.getPredefinedCategories();
            return ResponseEntity.ok(predefinedCategories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategoryAndAssociatedFoods(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategoryAndAssociatedFoods(categoryId);
            return ResponseEntity.ok("Category and associated foods deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting category: " + e.getMessage());
        }
    }

}
