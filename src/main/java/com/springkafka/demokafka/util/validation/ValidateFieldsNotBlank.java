package com.springkafka.demokafka.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.util.validation  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 9:40 PM
 */

@Documented
@Constraint(validatedBy = ValidateFieldsNotBlankValidator.class)
@Target({ ElementType.TYPE })  // áp dụng cho class
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateFieldsNotBlank {
    String message() default "Các field không được để trống";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // Danh sách field cần check
    String[] fields();
}
