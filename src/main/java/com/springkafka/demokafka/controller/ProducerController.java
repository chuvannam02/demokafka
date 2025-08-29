package com.springkafka.demokafka.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.controller  *
 * @Author: ChuVanNam
 * @Date: 8/28/2025
 * @Time: 10:32 AM
 */

@RestController
@RequestMapping("/api/messages")
public class ProducerController {


    private final KafkaTemplate<String, String> kafkaTemplate;


    @Value("${app.topics.main}")
    private String topic;


    public ProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @PostMapping
    public ResponseEntity<String> send(@RequestBody String payload) {
        kafkaTemplate.send(topic, payload);
        return ResponseEntity.ok("sent");
    }
}
