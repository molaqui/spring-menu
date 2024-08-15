package com.example.demo.DAO;


import com.example.demo.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTableNumberAndUserId(String tableNumber, Long userId);
    List<Order> findByOrderTypeAndUserId(String orderType, Long userId);
    Optional<Order> findByIdAndUserId(Long id, Long userId);

    long countByOrderTypeAndUserId(String orderType, Long userId);
}
