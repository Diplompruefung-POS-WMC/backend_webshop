package com.example.backend_webshop.strategy;

import com.example.backend_webshop.model.Behavior;
import com.example.backend_webshop.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HottestValue implements StatisticStrategy{
    HashMap<Long, Behavior> behaviorHashMap = new HashMap<>();

    public void loadBehaviorData(){
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm"));

        try {
            behaviorHashMap = objectMapper.readValue(
                    new File("productClicks.json"),
                    new TypeReference<>() {}
            );
        } catch (IOException e) {
            log.error("Error loading product clicks data from json: {}", e.getMessage());
        }
    }


    @Override
    public double calculateStatistic(List<Product> products) {
        loadBehaviorData();

        behaviorHashMap.forEach((aLong, behavior) ->
                log.info("Product ID: {}, Click Count: {}", aLong, behavior.getClickCount())
        );

        Map.Entry<Long, Behavior> hottestEntry = behaviorHashMap.entrySet()
                .stream()
                .max(Comparator.comparingInt(value -> value.getValue().getClickCount()))
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Error - No product was clicked yet")
                );

        return hottestEntry.getKey();
    }
}
