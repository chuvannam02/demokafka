package com.springkafka.demokafka.repository;

import com.springkafka.demokafka.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.repository.impl  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 12:12 AM
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh tại đây nếu cần
    // Derived query methods sẽ tự động được Spring Data JPA tạo ra dựa trên tên phương thức
    User findByUsername(String username);
    Long countByUsername(String username);
    Long countByEmail(String email);

    @Query("""
            SELECT u FROM User u
            WHERE (:keyword IS NULL OR u.username LIKE %:keyword% OR u.email LIKE %:keyword%)
            """)
    Page<User> findAllUsersByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
