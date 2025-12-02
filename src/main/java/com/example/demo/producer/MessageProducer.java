package com.example.demo.producer;

import com.example.demo.model.WarehouseData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@Component
@Profile("producer")
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send")
    public String sendMessage(
            @RequestParam(value = "message", defaultValue = "") String message,
            @RequestParam(value = "location", defaultValue = "north") String location
    ) {
        String topic = "warehouse-" +location.toLowerCase();
        kafkaTemplate.send(topic, message);
        return "Message '" + message + "' sent to " + topic;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody WarehouseData data) throws JsonProcessingException {
        String topic = "warehouse-" + data.getWarehouseID().toLowerCase();
        String jsonMessage = new ObjectMapper().writeValueAsString(data);
        kafkaTemplate.send(topic, jsonMessage);
        return "Message sent to " + topic;
    }

}