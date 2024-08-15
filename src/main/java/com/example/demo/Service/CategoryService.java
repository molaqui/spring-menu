package com.example.demo.Service;

import com.example.demo.DAO.CategoryRepository;
import com.example.demo.Entity.Category;
import com.example.demo.Entity.User;
import com.example.demo.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

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

    // Méthode pour compter les catégories par userId
    public long countCategoriesByUserId(Long userId) {
        return categoryRepository.countByUserId(userId);
    }
}
