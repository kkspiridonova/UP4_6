package com.example.sixthproject.controller;

import com.example.sixthproject.model.Address;
import com.example.sixthproject.model.Order;
import com.example.sixthproject.model.UserModel;
import com.example.sixthproject.repository.AddressRepository;
import com.example.sixthproject.service.CurrentUserService;
import com.example.sixthproject.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/orders")
@Tag(name = "Orders")
public class OrderController {

    @Autowired private OrderService orderService;
    @Autowired private CurrentUserService currentUserService;
    @Autowired private AddressRepository addressRepository;

    public static class CheckoutRequest {
        public Long addressId;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@Valid @RequestBody CheckoutRequest request) {
        UserModel user = currentUserService.getCurrentUserOrThrow();
        Address address = addressRepository.findById(request.addressId).orElseThrow();
        return ResponseEntity.ok(orderService.checkout(user, address));
    }
}


