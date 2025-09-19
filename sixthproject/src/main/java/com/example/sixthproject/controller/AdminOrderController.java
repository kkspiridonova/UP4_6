package com.example.sixthproject.controller;

import com.example.sixthproject.model.*;
import com.example.sixthproject.repository.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/admin/orders")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Admin - Orders")
public class AdminOrderController {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private PaymentRepository paymentRepository;

    @GetMapping
    public List<Order> listOrders() { return orderRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return orderRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam Order.OrderStatus status) {
        return orderRepository.findById(id).map(o -> {
            o.setStatus(status);
            return ResponseEntity.ok(orderRepository.save(o));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItem>> listItems(@PathVariable Long orderId) {
        return orderRepository.findById(orderId)
                .map(o -> ResponseEntity.ok(orderItemRepository.findByOrder(o)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderItem> addItem(@PathVariable Long orderId, @Valid @RequestBody OrderItem item) {
        return orderRepository.findById(orderId).map(o -> {
            item.setOrder(o);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderItemRepository.save(item));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        if (!orderItemRepository.existsById(itemId)) return ResponseEntity.notFound().build();
        orderItemRepository.deleteById(itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}/payment")
    public ResponseEntity<Payment> getPayment(@PathVariable Long orderId) {
        return orderRepository.findById(orderId)
                .flatMap(o -> paymentRepository.findByOrder(o))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{orderId}/payment")
    public ResponseEntity<Payment> createPayment(@PathVariable Long orderId, @Valid @RequestBody Payment p) {
        return orderRepository.findById(orderId).map(o -> {
            p.setOrder(o);
            return ResponseEntity.status(HttpStatus.CREATED).body(paymentRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/payment/{paymentId}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long paymentId, @Valid @RequestBody Payment p) {
        return paymentRepository.findById(paymentId).map(existing -> {
            existing.setAmount(p.getAmount());
            existing.setStatus(p.getStatus());
            return ResponseEntity.ok(paymentRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/payment/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId) {
        if (!paymentRepository.existsById(paymentId)) return ResponseEntity.notFound().build();
        paymentRepository.deleteById(paymentId);
        return ResponseEntity.noContent().build();
    }
}


