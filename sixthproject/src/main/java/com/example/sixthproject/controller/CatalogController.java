package com.example.sixthproject.controller;

import com.example.sixthproject.model.Category;
import com.example.sixthproject.model.Product;
import com.example.sixthproject.repository.BrandRepository;
import com.example.sixthproject.model.Brand;
import com.example.sixthproject.service.CatalogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/catalog")
@Tag(name = "Catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private BrandRepository brandRepository;

    @GetMapping("/products")
    public Page<Product> list(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int size) {
        return catalogService.listProducts(page, size);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> categories() {
        return ResponseEntity.ok(catalogService.listCategories());
    }

    @GetMapping("/brands")
    public ResponseEntity<?> brands() {
        return ResponseEntity.ok(brandRepository.findAll());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SELLER')")
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(catalogService.createProduct(product));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SELLER')")
    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.ok(catalogService.createCategory(category));
    }
}


