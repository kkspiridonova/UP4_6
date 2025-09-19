package com.example.sixthproject.controller;

import com.example.sixthproject.model.Address;
import com.example.sixthproject.repository.AddressRepository;
import com.example.sixthproject.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/admin/addresses")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Admin - Addresses")
public class AdminAddressController {

    @Autowired private AddressRepository addressRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping
    public List<Address> list() { return addressRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Address> get(@PathVariable Long id) {
        return addressRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Address> create(@Valid @RequestBody Address a) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressRepository.save(a));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> update(@PathVariable Long id, @Valid @RequestBody Address a) {
        return addressRepository.findById(id).map(existing -> {
            existing.setStreet(a.getStreet());
            existing.setCity(a.getCity());
            existing.setCountry(a.getCountry());
            existing.setPostalCode(a.getPostalCode());
            existing.setUser(a.getUser());
            return ResponseEntity.ok(addressRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!addressRepository.existsById(id)) return ResponseEntity.notFound().build();
        addressRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}




