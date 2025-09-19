package com.example.sixthproject.controller;

import com.example.sixthproject.model.Address;
import com.example.sixthproject.model.Order;
import com.example.sixthproject.model.UserModel;
import com.example.sixthproject.repository.AddressRepository;
import com.example.sixthproject.repository.OrderRepository;
import com.example.sixthproject.service.CurrentUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/v1/api/me")
@Tag(name = "Me")
public class MeController {

    @Autowired private CurrentUserService currentUserService;
    @Autowired private AddressRepository addressRepository;
    @Autowired private OrderRepository orderRepository;

    @GetMapping("/addresses")
    public ResponseEntity<List<Address>> myAddresses() {
        UserModel user = currentUserService.getCurrentUserOrThrow();
        return ResponseEntity.ok(addressRepository.findByUser(user));
    }

    @PostMapping("/addresses")
    public ResponseEntity<Address> createMyAddress(@Valid @RequestBody Address request) {
        UserModel user = currentUserService.getCurrentUserOrThrow();
        Address addr = new Address();
        addr.setStreet(request.getStreet());
        addr.setCity(request.getCity());
        addr.setCountry(request.getCountry());
        addr.setPostalCode(request.getPostalCode());
        addr.setUser(user);
        return ResponseEntity.ok(addressRepository.save(addr));
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<Address> updateMyAddress(@PathVariable Long id, @Valid @RequestBody Address request) {
        UserModel user = currentUserService.getCurrentUserOrThrow();
        return addressRepository.findById(id)
                .filter(a -> a.getUser().getId().equals(user.getId()))
                .map(existing -> {
                    existing.setStreet(request.getStreet());
                    existing.setCity(request.getCity());
                    existing.setCountry(request.getCountry());
                    existing.setPostalCode(request.getPostalCode());
                    return ResponseEntity.ok(addressRepository.save(existing));
                })
                .orElseGet(() -> ResponseEntity.<Address>notFound().build());
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Object> deleteMyAddress(@PathVariable Long id) {
        UserModel user = currentUserService.getCurrentUserOrThrow();
        return addressRepository.findById(id)
                .map(a -> {
                    if (!a.getUser().getId().equals(user.getId())) {
                        return ResponseEntity.<Void>notFound().build();
                    }
                    addressRepository.delete(a);
                    return ResponseEntity.<Void>noContent().build();
                })
                .orElse(ResponseEntity.<Void>notFound().build());

    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> myOrders() {
        UserModel user = currentUserService.getCurrentUserOrThrow();
        return ResponseEntity.ok(orderRepository.findByUser(user));
    }
}


