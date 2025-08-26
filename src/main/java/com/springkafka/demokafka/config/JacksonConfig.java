package com.springkafka.demokafka.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.config  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 4:32 PM
 */

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        
        // Create a custom deserializer that can handle multiple datetime formats
        LocalDateTimeDeserializer deserializer = new LocalDateTimeDeserializer(
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        ) {
            @Override
            public LocalDateTime deserialize(com.fasterxml.jackson.core.JsonParser p, 
                                          com.fasterxml.jackson.databind.DeserializationContext ctxt) 
                    throws java.io.IOException {
                String text = p.getText();
                try {
                    // Try the main format first (microseconds with 6 decimal places)
                    return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
                } catch (DateTimeParseException e1) {
                    try {
                        // Try with nanoseconds (9 decimal places)
                        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS"));
                    } catch (DateTimeParseException e2) {
                        try {
                            // Try with milliseconds (3 decimal places)
                            return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
                        } catch (DateTimeParseException e3) {
                            try {
                                // Try without decimal places
                                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                            } catch (DateTimeParseException e4) {
                                // If all formats fail, throw the original exception
                                throw new RuntimeException("Could not parse LocalDateTime: " + text + 
                                    ". Supported formats: yyyy-MM-dd'T'HH:mm:ss.SSSSSS, yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS, " +
                                    "yyyy-MM-dd'T'HH:mm:ss.SSS, yyyy-MM-dd'T'HH:mm:ss", e1);
                            }
                        }
                    }
                }
            }
        };
        
        // Use the same formatter for serialization
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        module.addDeserializer(LocalDateTime.class, deserializer);
        
        mapper.registerModule(module);

        // Disable writing dates as timestamps
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // Disable adjusting dates to context timezone
        mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        
        // Fail on unknown properties
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        // Include only non-null values
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        return mapper;
    }
}
