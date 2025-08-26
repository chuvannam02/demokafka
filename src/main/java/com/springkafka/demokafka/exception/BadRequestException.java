package com.springkafka.demokafka.exception;

import org.springframework.stereotype.Component;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.exception  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 10:15 AM
 */

public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 11231231280L;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
