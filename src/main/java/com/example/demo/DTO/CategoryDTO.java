package com.example.demo.DTO;


import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CategoryDTO {
    private Long id;
    private String name;
    private String image;  // Image encodée en base64
}