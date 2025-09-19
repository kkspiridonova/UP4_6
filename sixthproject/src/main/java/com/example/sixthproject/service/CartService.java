package com.example.sixthproject.service;

import com.example.sixthproject.model.*;
import com.example.sixthproject.repository.CartItemRepository;
import com.example.sixthproject.repository.CartRepository;
import com.example.sixthproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Cart getOrCreateCart(UserModel user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart c = new Cart();
            c.setUser(user);
            return cartRepository.save(c);
        });
    }

    @Transactional
    public Cart addItem(UserModel user, Long productId, int quantity) {
        Cart cart = getOrCreateCart(user);
        Product product = productRepository.findById(productId).orElseThrow();
        CartItem item = cartItemRepository.findByCartAndProduct(cart, product).orElseGet(() -> {
            CartItem ci = new CartItem();
            ci.setCart(cart);
            ci.setProduct(product);
            ci.setQuantity(0);
            cart.getItems().add(ci);
            return ci;
        });
        item.setQuantity(item.getQuantity() + Math.max(quantity, 1));
        cartItemRepository.save(item);
        return cart;
    }

    @Transactional
    public Cart updateItem(UserModel user, Long productId, int quantity) {
        Cart cart = getOrCreateCart(user);
        Product product = productRepository.findById(productId).orElseThrow();
        CartItem item = cartItemRepository.findByCartAndProduct(cart, product).orElseThrow();
        if (quantity <= 0) {
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
        return cart;
    }

    @Transactional
    public void clear(UserModel user) {
        Cart cart = getOrCreateCart(user);
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
    }

    @Transactional
    public Cart removeItem(UserModel user, Long productId) {
        Cart cart = getOrCreateCart(user);
        Product product = productRepository.findById(productId).orElseThrow();
        cartItemRepository.findByCartAndProduct(cart, product).ifPresent(ci -> {
            cart.getItems().remove(ci);
            cartItemRepository.delete(ci);
        });
        return cart;
    }
}


