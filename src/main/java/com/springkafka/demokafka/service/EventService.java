package com.springkafka.demokafka.service;

import java.util.concurrent.CompletableFuture;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.service  *
 * @Author: ChuVanNam
 * @Date: 8/26/2025
 * @Time: 11:04 AM
 */

public interface EventService {
    CompletableFuture<String> processEvent(String eventName, Integer duration);
}
