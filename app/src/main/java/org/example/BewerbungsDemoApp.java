package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class BewerbungsDemoApp {
    private static final Logger log = LoggerFactory.getLogger(BewerbungsDemoApp.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(BewerbungsDemoApp.class, args);
        } catch (Exception e) {
            log.error("Error starting the app: " + e.getMessage());
        }
    }
}