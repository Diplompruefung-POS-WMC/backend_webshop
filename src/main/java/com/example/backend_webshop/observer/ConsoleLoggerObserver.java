package com.example.backend_webshop.observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleLoggerObserver implements ProductObserver{

    @Override
    public void update(Long productId) {
        log.info("User requested details on product with id: " + productId);
    }
}
