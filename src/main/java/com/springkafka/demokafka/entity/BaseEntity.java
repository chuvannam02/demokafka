package com.springkafka.demokafka.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.entity  *
 * @Author: ChuVanNam
 * @Date: 8/23/2025
 * @Time: 9:05 PM
 */

// abstract class là lớp không thể khởi tạo đối tượng, chỉ có thể kế thừa
// Interface là một tập hợp các phương thức trừu tượng mà không có phần thân, các lớp con phải triển khai các phương thức này
@Getter
@Setter
// MappedSuperclass để các class con kế thừa có thể sử dụng các thuộc tính của lớp này
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    // @CreatedDate sẽ tự động gán giá trị ngày giờ hiện tại khi tạo mới bản ghi
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    // @UpdateTimestamp sẽ tự động gán giá trị ngày giờ hiện tại khi cập nhật bản ghi
    @UpdateTimestamp
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    // @CreatedBy, @LastModifiedBy: dùng với Spring Data JPA Auditing để tự động gán giá trị người tạo và người sửa bản ghi
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
}
