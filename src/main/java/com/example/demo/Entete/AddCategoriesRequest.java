package com.example.demo.Entete;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter @Getter
public class AddCategoriesRequest {
    private Long userId;
    private List<Long> categoryIds;
    private List<Long> foodIds;  // Nouvelle liste pour les food IDs sélectionnés

    // Getters and setters
}