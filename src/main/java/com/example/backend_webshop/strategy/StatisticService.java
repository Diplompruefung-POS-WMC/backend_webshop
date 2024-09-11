package com.example.backend_webshop.strategy;

import com.example.backend_webshop.factory.StatisticStrategyFactory;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    private final StatisticStrategyFactory statisticStrategyFactory;

    public StatisticService(StatisticStrategyFactory statisticStrategyFactory) {
        this.statisticStrategyFactory = statisticStrategyFactory;
    }

    public double calculateStatistic(String method) {
        StatisticStrategy statisticStrategy = statisticStrategyFactory.createStrategy(method);

        return statisticStrategy.calculateStatistic();
    }
}