package com.example.backend_webshop.database;

import com.example.backend_webshop.model.Category;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DBInit {
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void loadProducts() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Category> categories = objectMapper.readValue(
                    getClass().getResourceAsStream("/products.json"),
                    new TypeReference<>() {}
            );

            categories.forEach(category ->
                    category.getProducts().forEach(product ->
                            product.setCategory(category)));
            categoryRepository.saveAll(categories);
            log.info("Products successfully loaded");
        } catch (IOException e) {
            log.error("Error loading product data from json File: {}", e.getMessage());
        }
    }
}
