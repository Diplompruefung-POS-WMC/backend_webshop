package com.example.backend_webshop.observer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileLogger implements ObserverLogger{
    @Override
    public void update(Long productId) {
        log.info("Filelogger");
    }
}
