package com.example.backend_webshop.strategy;

import com.example.backend_webshop.factory.StatisticStrategyFactory;
import com.example.backend_webshop.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {
    private final StatisticStrategyFactory statisticStrategyFactory;

    public StatisticService(StatisticStrategyFactory statisticStrategyFactory) {
        this.statisticStrategyFactory = statisticStrategyFactory;
    }

    public double calculateStatistic(String method, List<Product> products) {
        StatisticStrategy statisticStrategy = statisticStrategyFactory.createStrategy(method);

        return statisticStrategy.calculateStatistic(products);
    }
}