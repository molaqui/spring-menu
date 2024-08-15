package com.example.demo.DTO;

import com.example.demo.Entity.Category;
import lombok.*;

// DTO sans @JsonIgnore
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private Long id;
    private String name;
    private byte[] image;
    private Double price;
    private String description;

}
