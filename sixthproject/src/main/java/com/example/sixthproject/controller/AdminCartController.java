package com.example.sixthproject.controller;

import com.example.sixthproject.model.Cart;
import com.example.sixthproject.model.CartItem;
import com.example.sixthproject.model.UserModel;
import com.example.sixthproject.repository.UserRepository;
import com.example.sixthproject.repository.CartItemRepository;
import com.example.sixthproject.repository.CartRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/admin/carts")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Admin - Carts")
public class AdminCartController {

    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping
    public List<Cart> list() { return cartRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> get(@PathVariable Long id) {
        return cartRepository.findById(id)
                .<ResponseEntity<Cart>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body((Cart) null));
    }

    @PostMapping
    public ResponseEntity<Cart> create(@RequestParam Long userId) {
        return userRepository.findById(userId).<ResponseEntity<Cart>>map(user -> {
            if (cartRepository.findByUser(user).isPresent()) {
                return ResponseEntity.badRequest().body(null);
            }
            Cart c = new Cart();
            c.setUser(user);
            return ResponseEntity.ok(cartRepository.save(c));
        }).orElse(ResponseEntity.status(404).body((Cart) null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!cartRepository.existsById(id)) return ResponseEntity.notFound().build();
        cartRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<CartItem>> items(@PathVariable Long id) {
        return cartRepository.findById(id)
                .<ResponseEntity<List<CartItem>>>map(c -> ResponseEntity.ok(c.getItems()))
                .orElse(ResponseEntity.status(404).body((List<CartItem>) null));
    }
}


