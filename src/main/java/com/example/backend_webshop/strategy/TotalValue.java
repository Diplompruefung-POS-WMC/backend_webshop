package com.example.backend_webshop.strategy;

import com.example.backend_webshop.model.Product;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TotalValue implements StatisticStrategy{
    @Override
    public double calculateStatistic(List<Product> products) {
        AtomicReference<Double> sum = new AtomicReference<>(0.0);

        products.forEach(product ->
                sum.updateAndGet(v -> v + product.getPrice() * product.getStock()));

        return Math.round(sum.get() * 100.0) / 100.0;
    }
}
