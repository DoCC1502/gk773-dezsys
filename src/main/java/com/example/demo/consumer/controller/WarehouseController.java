package com.example.demo.consumer.controller;

import com.example.demo.consumer.MessageConsumer;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@Profile("consumer")
public class WarehouseController {


    private final MessageConsumer consumer;

    public WarehouseController(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    // Abruf aller gesammelten Lagerdaten
    @GetMapping(path="/warehouse/data",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllData() {
        return consumer.getCollectedData();
    }
}
