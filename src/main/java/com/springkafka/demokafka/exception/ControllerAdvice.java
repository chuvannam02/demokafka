package com.springkafka.demokafka.exception;

import com.springkafka.demokafka.dto.out.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.exception  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 10:16 AM
 */

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequestException(BadRequestException ex) {
        // Log the exception message
        System.err.println("Bad Request: " + ex.getMessage());

        // Return a user-friendly error message
        return "Bad Request: " + ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex) {
        // Log the exception message
        System.err.println("An error occurred: " + ex.getMessage());

        // Return a generic error message
        return "An unexpected error occurred. Please try again later.";
    }

    // ✅ Bắt lỗi file vượt quá dung lượng
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, Object>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.PAYLOAD_TOO_LARGE.value());
        body.put("error", "File too large");
        body.put("message", "❌ File vượt quá dung lượng tối đa cho phép!");
        body.put("path", "/api/upload/chunk"); // bạn có thể lấy từ HttpServletRequest nếu cần

        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(body);
    }

    // Bắt Hibernate ConstraintViolation
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Database constraint violation: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                      HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorCode("VALIDATION_ERROR")
                .message("Dữ liệu không hợp lệ")
                .errors(errors) // ["username: không được để trống", "email: không hợp lệ", ...]
                .timestamp(LocalDateTime.now().toString())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    // Bắt SQL Exception trực tiếp
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("SQL error: " + ex.getMessage());
    }

    // Bắt chung các lỗi truy cập DB của Spring Data
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Database access error: " + ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
        // Log the exception message
        System.err.println("No handler found: " + ex.getMessage());

        // Return a user-friendly error message
        return "The requested resource was not found.";
    }
}
