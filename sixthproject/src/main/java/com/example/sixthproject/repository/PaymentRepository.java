package com.example.sixthproject.repository;

import com.example.sixthproject.model.Order;
import com.example.sixthproject.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder(Order order);
}




