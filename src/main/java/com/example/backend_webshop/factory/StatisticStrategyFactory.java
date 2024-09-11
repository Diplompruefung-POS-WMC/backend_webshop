package com.example.backend_webshop.factory;

import com.example.backend_webshop.strategy.AverageValue;
import com.example.backend_webshop.strategy.HottestValue;
import com.example.backend_webshop.strategy.StatisticStrategy;
import com.example.backend_webshop.strategy.TotalValue;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class StatisticStrategyFactory {

    public StatisticStrategy createStrategy(String method) {
        return switch (method) {
            case "hottest" -> new HottestValue();
            case "avg" -> new AverageValue();
            case "total" -> new TotalValue();
            default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error - no such method," +
                    " please use 'hottest', 'avg' or 'total'");
        };
    }
}
