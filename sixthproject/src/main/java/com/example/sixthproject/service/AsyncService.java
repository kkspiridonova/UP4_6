package com.example.sixthproject.service;

import com.example.sixthproject.model.Product;
import com.example.sixthproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

    @Autowired
    private ProductRepository productRepository;

    @Async("taskExecutor")
    public CompletableFuture<List<Product>> getAllProductsAsync() {
        try {
            Thread.sleep(500);
            List<Product> products = productRepository.findAll();
            return CompletableFuture.completedFuture(products);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<Product> getProductByIdAsync(Long id) {
        try {
            Thread.sleep(300);
            Product product = productRepository.findById(id).orElseThrow();
            return CompletableFuture.completedFuture(product);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async("taskExecutor")
    public CompletableFuture<String> processProductDataAsync(Product product) {
        try {
            Thread.sleep(400);
            String result = "Обработаны данные товара: " + product.getName();
            return CompletableFuture.completedFuture(result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(e);
        }
    }
}

