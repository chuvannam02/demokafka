package com.springkafka.demokafka.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.config  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 12:04 AM
 */

// Cung cấp thông tin về người dùng hiện tại để JPA Auditing có thể sử dụng
@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Lấy user từ SecurityContext, ví dụ với Spring Security:
        // return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
        return Optional.of("system"); // tạm thời fix cứng
    }
}
