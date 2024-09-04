package com.example.backend_webshop.database;

import com.example.backend_webshop.repository.CategoryRepository;
import com.example.backend_webshop.model.Category;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DBInit {
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void loadData(){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Category> categoryList = objectMapper.readValue(
                    getClass().getResourceAsStream("/products.json"),
                    new TypeReference<>() {}
            );

            categoryList.forEach(category -> category.getProducts()
                    .forEach(product -> product.setCategory(category)));


            categoryRepository.saveAll(categoryList);
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }

    }
}
