package com.springkafka.demokafka.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.springkafka.demokafka.dto.view.Views;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.dto.in.user  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 10:01 AM
 */

@Getter
@Setter
@MappedSuperclass
public abstract class BasePagedDTO {
    // --- Paging info ---
    private Integer page = 0;
    private Integer size = 10;
    private String sortOrder;        // "asc" hoặc "desc"
    private String propertiesSort;   // Tên trường sắp xếp

    // Fix: Use consistent datetime format that matches Jackson configuration
//    @JsonView(Views.Internal.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS", timezone = "Asia/Ho_Chi_Minh", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdDate;

//    @JsonView(Views.Internal.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS", timezone = "Asia/Ho_Chi_Minh", shape = JsonFormat.Shape.STRING)
    private LocalDateTime lastModifiedDate;

    private String createdBy;
    private String lastModifiedBy;

    // 👇 Field ảo, không lưu DB, chỉ trả ra JSON
    @JsonProperty("createdDateFormatted")
    public String getCreatedDateFormatted() {
        return createdDate != null
                ? createdDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                : null;
    }

    @JsonProperty("lastModifiedDateFormatted")
    public String getLastModifiedDateFormatted() {
        return lastModifiedDate != null
                ? lastModifiedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                : null;
    }
}
