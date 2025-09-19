package com.example.sixthproject.controller;

import com.example.sixthproject.model.Brand;
import com.example.sixthproject.repository.BrandRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/admin/brands")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Admin - Brands")
public class AdminBrandController {

    @Autowired private BrandRepository brandRepository;

    @GetMapping
    public List<Brand> list() { return brandRepository.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> get(@PathVariable Long id) {
        return brandRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Brand> create(@Valid @RequestBody Brand b) {
        return ResponseEntity.status(HttpStatus.CREATED).body(brandRepository.save(b));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> update(@PathVariable Long id, @Valid @RequestBody Brand b) {
        return brandRepository.findById(id).map(existing -> {
            existing.setName(b.getName());
            return ResponseEntity.ok(brandRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!brandRepository.existsById(id)) return ResponseEntity.notFound().build();
        brandRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}




