# DEZSYS_GK73_WAREHOUSE_MOM


# Übersicht
Dieses Projekt demonstriert die Funktionsweise einer Message Oriented Middleware (MOM) mit Apache Kafka. Ziel ist der Datenaustausch zwischen mehreren Lagerstandorten und einer zentralen Zentrale.

 * Jede Stunde oder bei Bedarf werden die Lagerbestände der Standorte erfasst.
 * Daten werden über Kafka Topics ausgetauscht.
 * Aggregierte Lagerbestände stehen über eine REST-Schnittstelle in JSON oder XML zur Verfügung.

# Code Snippets

## 1. Producer

```java
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
```

## 2. Consumer

```java
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
```

## 3. Controller

```java
    private final MessageConsumer consumer;

    public WarehouseController(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    // Abruf aller gesammelten Lagerdaten
    @GetMapping(path="/warehouse/data",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllData() {
        return consumer.getCollectedData();
    }
```

# Fragestellung

1. Eigenschaften einer MOM:
   Asynchron, lose gekoppelt, zuverlässig, skalierbar, persistente Speicherung, Lastverteilung.

2. Transiente und synchrone Kommunikation:
   Transient = nur während Laufzeit, synchron = Sender wartet auf Antwort.

3. JMS Queue:
   Punkt-zu-Punkt, 1 Producer → Queue → 1 Consumer, FIFO, genau einmalige Zustellung.

4. JMS Klassen:
   ConnectionFactory, Connection, Session, MessageProducer, MessageConsumer, Destination.

5. JMS Topic:
   Publish/Subscribe, alle Subscriber erhalten Nachrichten.

6. Lose gekoppeltes System:
   Sender und Empfänger wissen nichts voneinander, asynchrone Kommunikation, Beispiel: Kafka.

# Links & Dokumente

*   Grundlagen Message Oriented Middleware: [Presentation](https://elearning.tgm.ac.at/pluginfile.php/119077/mod_resource/content/1/dezsys_mom_einfuehrung.pdf)
*   Middleware:  [Apache Kafka](https://kafka.apache.org/quickstart)  
*   [Apache Kafka | Getting Started](https://kafka.apache.org/documentation/#gettingStarted)   

    
  https://medium.com/@abhishekranjandev/a-comprehensive-guide-to-integrating-kafka-in-a-spring-boot-application-a4b912aee62e
  https://spring.io/guides/gs/messaging-jms/  
  https://medium.com/@mailshine/activemq-getting-started-with-springboot-a0c3c960356e   
  http://www.academictutorials.com/jms/jms-introduction.asp   
  http://docs.oracle.com/javaee/1.4/tutorial/doc/JMS.html#wp84181    
  https://www.oracle.com/java/technologies/java-message-service.html   
  http://www.oracle.com/technetwork/articles/java/introjms-1577110.html  
  https://spring.io/guides/gs/messaging-jms  
  https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html  
  https://dzone.com/articles/using-jms-in-spring-boot-1  
