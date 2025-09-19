package com.example.sixthproject.service;

import com.example.sixthproject.model.Category;
import com.example.sixthproject.model.Product;
import com.example.sixthproject.repository.CategoryRepository;
import com.example.sixthproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> listProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public List<Category> listCategories() { return categoryRepository.findAll(); }

    public Product createProduct(Product product) { return productRepository.save(product); }

    public Category createCategory(Category category) { return categoryRepository.save(category); }
}




