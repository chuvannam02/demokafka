package com.springkafka.demokafka.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {
    private static final Logger log = LogManager.getLogger(HelloController.class);

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        log.info("Hello endpoint called");
        log.warn("A sample WARN log with MDC fields");
        return Map.of("message", "hello", "status", "OK");
    }
}
