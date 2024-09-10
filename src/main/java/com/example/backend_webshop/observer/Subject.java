package com.example.backend_webshop.observer;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
    List<ObserverLogger> observers = new ArrayList<>();

    void addObserver(ObserverLogger observerLogger);
    void removeObserver(ObserverLogger observerLogger);
    void notifyObservers(Long productId);
}
