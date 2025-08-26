package com.springkafka.demokafka.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.AOP  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 10:24 AM
 */

@Target(ElementType.METHOD)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface AdditionalRequestTrack {
}
