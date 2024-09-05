package com.example.backend_webshop.observer;

import com.example.backend_webshop.models.ProductBehavior;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class FileLoggerObserver implements ProductObserver {
    // static is important for caching!!
    private static Map<Long, ProductBehavior> behaviorData;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).setDateFormat(
            new SimpleDateFormat("yyyy-MM-dd HH:mm")
    );

    @PostConstruct
    public void loadBehaviorData() {
        try {
            // load data from behavior.json
            behaviorData = objectMapper.readValue(
                    getClass().getResourceAsStream("/behavior.json"),
                    new TypeReference<>() {}
            );

            behaviorData.forEach((productId, productBehavior) ->
                    log.info("Product ID: " + productId + ", Click Count: " + productBehavior.getClickCount())
            );
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Einlesen der behavior.json", e);
        }
    }

    @Override
    public void update(Long productId) {
        ProductBehavior productBehavior = behaviorData.getOrDefault(productId, new ProductBehavior(0, new Date()));

        productBehavior.updateClickCount();
        behaviorData.put(productId, productBehavior);

        try {
            objectMapper.writeValue(new File("behavior.json"), behaviorData);
        } catch (IOException e) {
            log.error("Error writing behavior.json", e);
        }
    }
}
