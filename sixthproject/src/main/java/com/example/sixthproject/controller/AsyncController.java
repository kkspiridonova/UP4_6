package com.example.sixthproject.controller;

import com.example.sixthproject.model.Product;
import com.example.sixthproject.service.AsyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/api/async")
@Tag(name = "Async Operations")
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/products")
    @Operation(summary = "Получить все товары асинхронно")
    @ApiResponse(responseCode = "200")
    public CompletableFuture<ResponseEntity<List<Product>>> getAllProductsAsync() {
        return asyncService.getAllProductsAsync()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/products/{id}")
    @Operation(summary = "Получить товар по ID асинхронно")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")
    })
    public CompletableFuture<ResponseEntity<Product>> getProductByIdAsync(@PathVariable Long id) {
        return asyncService.getProductByIdAsync(id)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/process")
    @Operation(summary = "Обработать данные товара асинхронно")
    @ApiResponse(responseCode = "200")
    public CompletableFuture<ResponseEntity<String>> processProductDataAsync(@RequestBody Product product) {
        return asyncService.processProductDataAsync(product)
                .thenApply(ResponseEntity::ok);
    }
}

