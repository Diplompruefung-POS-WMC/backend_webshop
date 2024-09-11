package com.example.backend_webshop.strategy;

import com.example.backend_webshop.model.Product;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AverageValue implements StatisticStrategy{
    @Override
    public double calculateStatistic(List<Product> products) {
        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        AtomicInteger totalStock = new AtomicInteger();
        double average;

        products.forEach(product ->
                sum.updateAndGet(v -> v + product.getPrice() * product.getStock())
        );

        products.forEach(product ->
                totalStock.addAndGet(product.getStock())
        );

        average = sum.get() / totalStock.get();

        return Math.round(average * 100.0) / 100.0;
    }
}
