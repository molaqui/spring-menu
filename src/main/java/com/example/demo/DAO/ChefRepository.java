package com.example.demo.DAO;

import com.example.demo.Entity.Chef;
import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChefRepository extends JpaRepository<Chef, Long> {
    List<Chef> findByUserId(Long userId);
    Optional<Chef> findByIdAndUserId(Long id, Long userId);
    List<Chef> findByUser(User user);
}
