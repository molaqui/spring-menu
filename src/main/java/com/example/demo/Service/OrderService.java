package com.example.demo.Service;

import com.example.demo.DAO.OrderRepository;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderItem;
import com.example.demo.Entity.User;
import com.example.demo.DAO.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public Order saveOrder(Order order, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            double total = 0;
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
                total += item.getPrice() * item.getQuantity();
            }
            order.setTotal(total);
            order.setUser(userOptional.get());
            return orderRepository.save(order);
        }
        throw new RuntimeException("User not found");
    }

    public List<Order> getOrdersByTableNumber(String tableNumber, Long userId) {
        return orderRepository.findByTableNumberAndUserId(tableNumber, userId);
    }

    public List<Order> getDeliveryOrders(Long userId) {
        return orderRepository.findByOrderTypeAndUserId("delivery", userId);
    }

    public List<Order> getTableOrders(Long userId) {
        return orderRepository.findByOrderTypeAndUserId("table", userId);
    }

    public Order updateOrderStatus(Long orderId, Boolean status, Long userId) {
        Optional<Order> optionalOrder = orderRepository.findByIdAndUserId(orderId, userId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    public void deleteOrder(Long orderId, Long userId) {
        Optional<Order> optionalOrder = orderRepository.findByIdAndUserId(orderId, userId);
        if (optionalOrder.isPresent()) {
            orderRepository.deleteById(orderId);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    // Compter les ordres par type (table ou livraison) pour un utilisateur spécifique
    public long countOrdersByType(String orderType, Long userId) {
        return orderRepository.countByOrderTypeAndUserId(orderType, userId);
    }
}
