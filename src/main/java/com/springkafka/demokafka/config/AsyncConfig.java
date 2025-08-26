package com.springkafka.demokafka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.config  *
 * @Author: ChuVanNam
 * @Date: 8/26/2025
 * @Time: 10:46 AM
 */

@Configuration
// Kích hoạt hỗ trợ lập trình bất đồng bộ trong Spring Application
public class AsyncConfig implements AsyncConfigurer {
    // Tuỳ chỉnh Executor để quản lý các luồng thực thi bất đồng bộ
    @Override
    public Executor getAsyncExecutor() {
        // Tạo một ThreadPoolTaskExecutor với các cấu hình cụ thể
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // Cấu hình số lượng luồng trong pool
        executor.setCorePoolSize(3);
        // Cấu hình số lượng luồng tối đa trong pool
        executor.setMaxPoolSize(10);
        // Cấu hình kích thước hàng đợi
        executor.setQueueCapacity(50);
        // Cấu hình tiền tố tên luồng để dễ dàng nhận biết các luồng do Executor tạo ra
        executor.setThreadNamePrefix("MyExecutor-");
        executor.initialize();
        return executor;
    }
}
