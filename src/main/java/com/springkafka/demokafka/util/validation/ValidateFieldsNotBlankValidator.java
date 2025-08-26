package com.springkafka.demokafka.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.util.validation  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 9:41 PM
 */

public class ValidateFieldsNotBlankValidator implements ConstraintValidator<ValidateFieldsNotBlank, Object> {

    private String[] fields;

    @Override
    public void initialize(ValidateFieldsNotBlank constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        try {
            Class<?> clazz = value.getClass();

            for (String fieldName : fields) {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(value);

                if (fieldValue == null) {
                    context.buildConstraintViolationWithTemplate(fieldName + " không được null")
                            .addConstraintViolation();
                    valid = false;
                } else if (fieldValue instanceof String && ((String) fieldValue).trim().isEmpty()) {
                    context.buildConstraintViolationWithTemplate(fieldName + " không được để trống")
                            .addConstraintViolation();
                    valid = false;
                } else if (fieldValue instanceof java.util.Collection && ((java.util.Collection<?>) fieldValue).isEmpty()) {
                    context.buildConstraintViolationWithTemplate(fieldName + " không được để trống")
                            .addConstraintViolation();
                    valid = false;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Lỗi khi validate field", e);
        }

        return valid;
    }

}