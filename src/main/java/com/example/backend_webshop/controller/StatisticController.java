package com.example.backend_webshop.controller;

import com.example.backend_webshop.strategy.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping
    public ResponseEntity<Double> getStatistic(
            @RequestParam String method
    ) {
        Double statistic = statisticService.calculateStatistic(method);
        // Rückgabe des berechneten Wertes
        return ResponseEntity.ok(statistic);
    }
}
