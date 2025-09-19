package com.example.sixthproject.controller;

import com.example.sixthproject.dto.CartItemRequest;
import com.example.sixthproject.model.Cart;
import com.example.sixthproject.model.UserModel;
import com.example.sixthproject.service.CartService;
import com.example.sixthproject.service.CurrentUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/cart")
@Tag(name = "Cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CurrentUserService currentUserService;

    @GetMapping
    public ResponseEntity<Cart> getCart() {
        UserModel user = currentUserService.getCurrentUserOrThrow();
        return ResponseEntity.ok(cartService.getOrCreateCart(user));
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addItem(@Valid @RequestBody CartItemRequest request) {
        UserModel user = currentUserService.getCurrentUserOrThrow();
        return ResponseEntity.ok(cartService.addItem(user, request.getProductId(), request.getQuantity()));
    }

    @PutMapping("/items")
    public ResponseEntity<Cart> updateItem(@Valid @RequestBody CartItemRequest request) {
        UserModel user = currentUserService.getCurrentUserOrThrow();
        return ResponseEntity.ok(cartService.updateItem(user, request.getProductId(), request.getQuantity()));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Cart> removeItem(@PathVariable Long productId) {
        UserModel user = currentUserService.getCurrentUserOrThrow();
        return ResponseEntity.ok(cartService.removeItem(user, productId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clear() {
        UserModel user = currentUserService.getCurrentUserOrThrow();
        cartService.clear(user);
        return ResponseEntity.noContent().build();
    }
}


