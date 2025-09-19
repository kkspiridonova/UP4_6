package com.example.sixthproject.repository;

import com.example.sixthproject.model.Order;
import com.example.sixthproject.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}




