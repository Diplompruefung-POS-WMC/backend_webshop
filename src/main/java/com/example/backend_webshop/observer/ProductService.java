package com.example.backend_webshop.observer;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final List<ProductObserver> observers = new ArrayList<>();

    public ProductService() {
        addObserver(new ConsoleLoggerObserver());
        addObserver(new FileLoggerObserver());
    }

    public void addObserver(ProductObserver observer){
        observers.add(observer);
    }

    public void removeObserver(ProductObserver observer){
        observers.remove(observer);
    }

    public void notifyObserver(Long productId){
        observers.forEach(observer -> observer.update(productId));
    }
}
