package com.example.demo.DTO;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MenuDTO {
    private Long userId;
    private Long categoryId;
    private List<Long> foodIds;
}
