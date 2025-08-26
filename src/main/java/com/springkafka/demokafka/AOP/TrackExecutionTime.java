package com.springkafka.demokafka.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.AOP  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 8:44 AM
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
// @interface TrackExecutionTime khác gì với interface thường?
// @interface là một annotation trong Java, được sử dụng để định nghĩa các chú thích (annotations).
// còn interface là một giao diện (interface) trong Java, được sử dụng để định nghĩa các phương thức mà các lớp khác phải triển khai.
public @interface TrackExecutionTime {
}
