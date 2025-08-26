package com.springkafka.demokafka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.config  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 12:03 AM
 */

// Kích hoạt JPA Auditing để tự động cập nhật các trường createdDate, lastModifiedDate, createdBy, lastModifiedBy
// Bật Jpa Auditing
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {
}
