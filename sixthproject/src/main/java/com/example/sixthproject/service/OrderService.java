package com.example.sixthproject.service;

import com.example.sixthproject.model.*;
import com.example.sixthproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public Order checkout(UserModel user, Address shippingAddress) {
        Cart cart = cartRepository.findByUser(user).orElseThrow();
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.OrderStatus.CREATED);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setShippingAddress(shippingAddress);
        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem ci : cartItemRepository.findByCart(cart)) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            oi.setUnitPrice(ci.getProduct().getPrice());
            orderItemRepository.save(oi);
            total = total.add(ci.getProduct().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
        }
        order.setTotalAmount(total);
        order = orderRepository.save(order);
        cartItemRepository.deleteAll(cart.getItems());
        return order;
    }
}


