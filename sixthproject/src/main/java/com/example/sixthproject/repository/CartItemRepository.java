package com.example.sixthproject.repository;

import com.example.sixthproject.model.Cart;
import com.example.sixthproject.model.CartItem;
import com.example.sixthproject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}

