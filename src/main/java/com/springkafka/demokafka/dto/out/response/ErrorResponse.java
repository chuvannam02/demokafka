package com.springkafka.demokafka.dto.out.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.dto.out.response  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 9:31 PM
 */

@Data
@Builder
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private List<String> errors;
    private String errorCode;
    private String timestamp;
    private String path;
}
