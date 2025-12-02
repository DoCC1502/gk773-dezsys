package com.example.demo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("consumer")
public class ConsumerMain {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsumerMain.class);
        app.setAdditionalProfiles("consumer");
        app.run(args);
    }
}