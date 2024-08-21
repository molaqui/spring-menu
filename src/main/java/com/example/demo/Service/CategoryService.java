package com.example.demo.Service;

import com.example.demo.DAO.CategoryRepository;
import com.example.demo.DAO.FoodImageRepository;
import com.example.demo.DAO.FoodRepository;
import com.example.demo.Entity.Category;
import com.example.demo.Entity.Food;
import com.example.demo.Entity.FoodImage;
import com.example.demo.Entity.User;
import com.example.demo.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FoodImageRepository foodImageRepository;


    public List<Category> getAllCategories(Long userId) {
        return categoryRepository.findByUserId(userId);
    }

    public Category saveCategory(Category category, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            category.setUser(userOptional.get());
            return categoryRepository.save(category);
        }
        throw new RuntimeException("User not found");
    }

    public Optional<Category> findByName(String name, Long userId) {
        return categoryRepository.findByNameAndUserId(name, userId);
    }

    ////////////////Added////////////////::::

    public List<Category> getPredefinedCategories() {
        // Fetch categories associated with the user having id 26
        return categoryRepository.findByUserId(26L);
    }

    public void addSelectedCategoriesToUser(Long userId, List<Long> categoryIds, List<Long> foodIds) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Category> predefinedCategories = categoryRepository.findByUserId(26L);

        List<Category> selectedCategories = predefinedCategories.stream()
                .filter(category -> categoryIds.contains(category.getId()))
                .collect(Collectors.toList());

        for (Category predefinedCategory : selectedCategories) {
            Optional<Category> existingCategory = findByName(predefinedCategory.getName(), userId);
            if (existingCategory.isPresent()) {
                continue; // Skip the category if it already exists
            }

            Category userCategory = new Category();
            userCategory.setName(predefinedCategory.getName());
            userCategory.setImage(predefinedCategory.getImage());
            userCategory.setUser(user);

            Category savedUserCategory = categoryRepository.save(userCategory);

            for (Food predefinedFood : predefinedCategory.getFoods()) {
                if (foodIds != null && !foodIds.contains(predefinedFood.getId())) {
                    continue; // Skip the food if it's not in the selected food IDs
                }

                Food userFood = new Food();
                userFood.setName(predefinedFood.getName());
                userFood.setPrice(predefinedFood.getPrice());
                userFood.setDescription(predefinedFood.getDescription());
                userFood.setCategory(savedUserCategory);
                userFood.setUser(user);

                Food savedUserFood = foodRepository.save(userFood);

                for (FoodImage predefinedFoodImage : predefinedFood.getImages()) {
                    FoodImage userFoodImage = new FoodImage();
                    userFoodImage.setImage(predefinedFoodImage.getImage());
                    userFoodImage.setFood(savedUserFood);

                    foodImageRepository.save(userFoodImage);
                }

                userFood.setImages(predefinedFood.getImages().stream().map(image -> {
                    FoodImage clonedImage = new FoodImage();
                    clonedImage.setImage(image.getImage());
                    clonedImage.setFood(savedUserFood);
                    return clonedImage;
                }).collect(Collectors.toList()));

                foodRepository.save(userFood);
            }
        }

        ////////////////Added////////////////::::


        // Méthode pour compter les catégories par userId


    }

    public long countCategoriesByUserId(Long userId) {
        return categoryRepository.countByUserId(userId);
    }



    @Transactional
    public void deleteCategoryAndAssociatedFoods(Long categoryId) {
        // Suppression des foods associés à la catégorie
        foodRepository.deleteByCategoryId(categoryId);
        // Suppression de la catégorie
        categoryRepository.deleteById(categoryId);
    }
}