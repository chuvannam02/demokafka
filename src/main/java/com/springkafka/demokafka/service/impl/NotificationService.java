package com.springkafka.demokafka.service.impl;

import com.springkafka.demokafka.service.NotificationInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.service.impl  *
 * @Author: ChuVanNam
 * @Date: 8/26/2025
 * @Time: 10:51 AM
 */

@Service
@Slf4j
public class NotificationService implements NotificationInterface {

    @Override
    @Async
    public void sendNotification(String user) {
        System.out.println("Gửi email cho: " + user + " - " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000); // giả lập thao tác lâu
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hoàn thành gửi email cho: " + user);
    }

    @Override
    @Async
    public CompletableFuture<String> logAction(String user) {
        System.out.println("Ghi log cho: " + user + " - " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture("Đã log cho " + user);
    }
}
