// ProducerMain.java
package com.example.demo.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("producer")
public class ProducerMain {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProducerMain.class);
        app.setAdditionalProfiles("producer");
        app.run(args);
    }
}