package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MessageConsumer {

    private final List<String> collectedData = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = {"warehouse-north", "warehouse-south", "warehouse-east", "warehouse-west"})
    public void processMessage(String content) {
        System.out.println("Received: " + content);

        // In zentrale Liste speichern
        collectedData.add(content);

        // Logging in Datei
        try (FileWriter fw = new FileWriter("warehouse.log", true)) {
            fw.write(content + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter für REST Controller
    public List<String> getCollectedData() {
        return collectedData;
    }

}