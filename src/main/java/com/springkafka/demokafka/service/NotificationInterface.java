package com.springkafka.demokafka.service;

import java.util.concurrent.CompletableFuture;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.service  *
 * @Author: ChuVanNam
 * @Date: 8/26/2025
 * @Time: 10:50 AM
 */

public interface NotificationInterface {
    void sendNotification(String user);
    CompletableFuture<String> logAction(String user);
}
