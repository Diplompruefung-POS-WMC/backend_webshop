package com.example.backend_webshop.observer;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//mit @Service können observers registriert werden, indem über die betroffenen Klassen @Component geschrieben wird
//wichtig ist ein Konstruktor mit @Autowired!! @RequiredArgsConstruktor funktioniert NICHT!!
@Service
public class ProductService implements Subject{
    private final List<ObserverLogger> observers;

    @Autowired
    public ProductService(List<ObserverLogger> observers) {
        this.observers = observers;
    }


    @Override
    public void addObserver(ObserverLogger observerLogger) {
        observers.add(observerLogger);
    }

    @Override
    public void removeObserver(ObserverLogger observerLogger) {
        observers.remove(observerLogger);
    }

    @Override
    public void notifyObservers(Long productId) {
        observers.forEach(observerLogger ->
                observerLogger.update(productId));
    }
}
