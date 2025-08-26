package com.springkafka.demokafka.dto.in.user;

import com.springkafka.demokafka.dto.BasePagedDTO;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.dto.in.user  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 4:13 PM
 */

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserListFilterDTO extends BasePagedDTO {
    String keyword; // Từ khóa tìm kiếm, có thể là tên, email hoặc số điện thoại
    Boolean isActive; // Trạng thái hoạt động của người dùng, có thể null để không lọc theo trạng thái này
}
