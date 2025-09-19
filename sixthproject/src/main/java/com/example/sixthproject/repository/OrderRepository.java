package com.example.sixthproject.repository;

import com.example.sixthproject.model.Order;
import com.example.sixthproject.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(UserModel user);
}




