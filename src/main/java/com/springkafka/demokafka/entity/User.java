package com.springkafka.demokafka.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.entity  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 12:10 AM
 */

@Entity
@Table(name = "users")
// Lớp User kế thừa từ BaseEntity để sử dụng các trường createdDate, lastModifiedDate, createdBy, lastModifiedBy
//👉 Khi insert, createdAt, createdBy sẽ được set.
//👉 Khi update, updatedAt, updatedBy sẽ tự động thay đổi.
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Chỉ bao gồm các trường không null trong JSON
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "username", unique = true, nullable = false, length = 50, updatable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    // transient: không lưu trường này vào cơ sở dữ liệu
//    private transient String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Chỉ cho phép ghi, không cho phép đọc
    private String password;
}

