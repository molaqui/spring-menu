package com.example.demo.DAO;



import com.example.demo.Entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByUserId(Long userId);
}
