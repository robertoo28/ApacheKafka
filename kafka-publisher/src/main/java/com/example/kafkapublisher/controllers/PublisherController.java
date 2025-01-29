package com.example.kafkapublisher.controllers;
import com.example.kafkapublisher.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publisher")
public class PublisherController {
    private final KafkaProducerService kafkaProducerService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public PublisherController(KafkaProducerService kafkaProducerService, KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaProducerService = kafkaProducerService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        kafkaProducerService.sendMessage(message);
        return ResponseEntity.ok("Mensaje enviado a Kafka: " + message);
    }
    @PostMapping("/sendToAnotherTopic")
    public ResponseEntity<String> sendToAnotherTopic(@RequestBody String message) {
        kafkaTemplate.send("another-topic", message);
        return ResponseEntity.ok("Mensaje enviado a another-topic: " + message);
    }

}

