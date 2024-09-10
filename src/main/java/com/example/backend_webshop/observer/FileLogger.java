package com.example.backend_webshop.observer;


import com.example.backend_webshop.model.Behavior;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Component
public class FileLogger implements ObserverLogger{
    private static HashMap<Long, Behavior> behaviorHashMap = new HashMap<>();
    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm"));
    @PostConstruct
    public void loadBehaviorData(){
        try {
            behaviorHashMap = objectMapper.readValue(
                    getClass().getResourceAsStream("/behavior.json"),
                    new TypeReference<>() {}
            );
        } catch (IOException e) {
            log.error("Error loading behavior data from json: {}", e.getMessage());
        }
    }

    @Override
    public void update(Long productId) {
        Behavior productBehavior = behaviorHashMap.getOrDefault(productId, new Behavior(0, new Date()));

        productBehavior.updateClickCount();
        behaviorHashMap.put(productId, productBehavior);

        try {
            objectMapper.writeValue(new File("productClicks.json"), behaviorHashMap);
        } catch (IOException e) {
            log.error("Error writing behavior.json", e);
        }
    }
}
