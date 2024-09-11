package com.example.backend_webshop.controller;

import com.example.backend_webshop.database.ProductRepository;
import com.example.backend_webshop.model.Product;
import com.example.backend_webshop.strategy.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
@CrossOrigin
public class StatisticController {
    private final StatisticService statisticService;
    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<Double> getStatistic(
            @RequestParam String method
    ) {
        List<Product> products = productRepository.findAll();
        Double statistic = statisticService.calculateStatistic(method, products);
        // Rückgabe des berechneten Wertes
        return ResponseEntity.ok(statistic);
    }
}
