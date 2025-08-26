package com.springkafka.demokafka.dto.out.response;

import lombok.Data;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.dto.out.response  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 10:18 AM
 */

@Data
public class BaseResponse {
    private String message;
    private int statusCode;
    private Object data;

    public BaseResponse(String message, int statusCode, Object data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public BaseResponse() {
        // Default constructor
    }
}
