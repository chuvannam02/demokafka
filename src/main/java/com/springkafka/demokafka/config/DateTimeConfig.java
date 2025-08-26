package com.springkafka.demokafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.format.DateTimeFormatter;

/**
 * Configuration for consistent datetime handling across the application
 */
@Configuration
public class DateTimeConfig {

    /**
     * Primary datetime formatter for the application
     * Uses microseconds precision (6 decimal places) to match PostgreSQL timestamp precision
     */
    @Bean
    @Primary
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
    }

    /**
     * Alternative formatter for cases where microseconds might not be present
     */
    @Bean("fallbackDateTimeFormatter")
    public DateTimeFormatter fallbackDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    }
}
