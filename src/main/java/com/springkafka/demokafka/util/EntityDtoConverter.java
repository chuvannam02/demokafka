package com.springkafka.demokafka.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utility class for converting between entities and DTOs
 * Handles datetime conversion issues that commonly occur with Jackson
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EntityDtoConverter {

    private final ObjectMapper objectMapper;

    /**
     * Convert entity to DTO with proper error handling
     */
    public <T> T convertToDto(Object entity, Class<T> dtoClass) {
        try {
            log.debug("Converting entity {} to DTO {}", 
                entity.getClass().getSimpleName(), dtoClass.getSimpleName());
            
            // First convert to JSON string, then back to DTO to avoid direct conversion issues
            String jsonString = objectMapper.writeValueAsString(entity);
            log.debug("Entity converted to JSON: {}", jsonString);
            
            T result = objectMapper.readValue(jsonString, dtoClass);
            log.debug("Successfully converted entity to DTO");
            return result;
        } catch (Exception e) {
            log.error("Error converting entity {} to DTO {}: {}", 
                entity.getClass().getSimpleName(), dtoClass.getSimpleName(), e.getMessage());
            log.error("Entity content: {}", entity);
            throw new RuntimeException("Failed to convert entity to DTO: " + e.getMessage(), e);
        }
    }

    /**
     * Convert DTO to entity with proper error handling
     */
    public <T> T convertToEntity(Object dto, Class<T> entityClass) {
        try {
            log.debug("Converting DTO {} to entity {}", 
                dto.getClass().getSimpleName(), entityClass.getSimpleName());
            
            // First convert to JSON string, then back to entity to avoid direct conversion issues
            String jsonString = objectMapper.writeValueAsString(dto);
            log.debug("DTO converted to JSON: {}", jsonString);
            
            T result = objectMapper.readValue(jsonString, entityClass);
            log.debug("Successfully converted DTO to entity");
            return result;
        } catch (Exception e) {
            log.error("Error converting DTO {} to entity {}: {}", 
                dto.getClass().getSimpleName(), entityClass.getSimpleName(), e.getMessage());
            log.error("DTO content: {}", dto);
            throw new RuntimeException("Failed to convert DTO to entity: " + e.getMessage(), e);
        }
    }

    /**
     * Convert list of entities to list of DTOs
     */
    public <T> List<T> convertToDtoList(List<?> entities, Class<T> dtoClass) {
        try {
            log.debug("Converting {} entities to DTO list of type {}", entities.size(), dtoClass.getSimpleName());
            
            CollectionType listType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, dtoClass);
            String jsonString = objectMapper.writeValueAsString(entities);
            log.debug("Entities converted to JSON array");
            
            List<T> result = objectMapper.readValue(jsonString, listType);
            log.debug("Successfully converted {} entities to DTOs", result.size());
            return result;
        } catch (Exception e) {
            log.error("Error converting entity list to DTO list: {}", e.getMessage());
            log.error("Entity list size: {}", entities.size());
            throw new RuntimeException("Failed to convert entity list to DTO list: " + e.getMessage(), e);
        }
    }
}
