package com.example.backend_webshop.controller;

import com.example.backend_webshop.model.Category;
import com.example.backend_webshop.repository.CategoryRepository;
import com.example.backend_webshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
