package com.example.sixthproject.repository;

import com.example.sixthproject.model.Cart;
import com.example.sixthproject.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(UserModel user);
}


