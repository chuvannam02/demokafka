package com.springkafka.demokafka.service.impl;

import com.springkafka.demokafka.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.service.impl  *
 * @Author: ChuVanNam
 * @Date: 8/26/2025
 * @Time: 11:05 AM
 */

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    @Override
    @Async
    public CompletableFuture<String> processEvent(String eventName, Integer duration) {
        System.out.println("Bắt đầu xử lý " + eventName + " trên thread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(duration * 1000L); // giả lập thời gian xử lý
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        String result = "Hoàn thành " + eventName;
        System.out.println(result);
        return CompletableFuture.completedFuture(result);
    }
}
