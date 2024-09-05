package com.example.backend_webshop.database;

import com.example.backend_webshop.models.Category;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    public void loadDataFromJson(){
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        try {
            List<Category> categories = objectMapper.readValue(
                    getClass().getResourceAsStream("/products.json"),
                    new TypeReference<>() {}
            );

            categories.forEach(category -> category.getProducts().forEach(
                    product -> product.setCategory(category)
            ));

            categoryRepository.saveAll(categories);
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Einlesen der products.json", e);
        }
    }
}
