package com.example.backend_webshop.strategy;

import com.example.backend_webshop.model.Product;

import java.util.List;

public interface StatisticStrategy {
    double calculateStatistic(List<Product> products);
}
