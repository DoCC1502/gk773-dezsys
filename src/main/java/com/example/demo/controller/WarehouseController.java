package com.example.demo.controller;

import com.example.demo.MessageConsumer;
import org.apache.kafka.common.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class WarehouseController {


    private final MessageConsumer consumer;

    public WarehouseController(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    // Abruf aller gesammelten Lagerdaten
    @GetMapping("/warehouse/data")
    public List<String> getAllData() {
        return consumer.getCollectedData();
    }
}
