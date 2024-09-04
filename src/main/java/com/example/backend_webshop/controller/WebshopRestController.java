package com.example.backend_webshop.controller;

import com.example.backend_webshop.model.Category;
import com.example.backend_webshop.model.Product;
import com.example.backend_webshop.repository.CategoryRepository;
import com.example.backend_webshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class WebshopRestController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsFromCategory(
            @PathVariable Long categoryId
    ){
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Error categoryId not exists"));

        List<Product> products = category.getProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(
            @PathVariable Long productId
    ){
        Product product  = productRepository.findById(productId).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Error productId not exists"));

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
