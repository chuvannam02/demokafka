package com.springkafka.demokafka.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.kafka.listener  *
 * @Author: ChuVanNam
 * @Date: 8/28/2025
 * @Time: 10:34 AM
 */

@Component
public class ConsumerListener {


    @KafkaListener(topics = "${app.topics.main}", groupId = "demo-group")
    public void listen(String payload) {
        System.out.println("Received: " + payload);


// Giả sử payload chứa chữ "error" -> ném exception để trigger DLQ
        if (payload != null && payload.contains("error")) {
            throw new RuntimeException("Fail processing payload: " + payload);
        }


// xử lý bình thường
        System.out.println("Processed: " + payload);
    }
}
