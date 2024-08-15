package com.example.demo.DAO;


import com.example.demo.Entity.HeaderImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeaderImageRepository extends JpaRepository<HeaderImage, Long> {
    List<HeaderImage> findByUserId(Long userId);
}
