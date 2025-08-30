package com.springkafka.demokafka.dto.in.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.springkafka.demokafka.dto.BasePagedDTO;
import com.springkafka.demokafka.dto.view.Views;
import com.springkafka.demokafka.util.validation.ValidateFieldsNotBlank;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.format.DateTimeFormatter;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.dto.in.user  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 9:59 AM
 */

@Data // Tự động tạo các phương thức getter, setter, toString, equals và hashCode
@JsonInclude(JsonInclude.Include.NON_NULL) // Chỉ bao gồm các trường không null trong JSON
@FieldDefaults(level = AccessLevel.PRIVATE) // Thiết lập mức độ truy cập cho các trường là PRIVATE
@JsonView(Views.Public.class)
@ValidateFieldsNotBlank(fields = {"username", "email", "name"}) // custom validate
@Builder
public class UserDTO extends BasePagedDTO {
    Long id; // ID của người dùng, có thể null nếu chưa được tạo
//    @NotBlank(message = "Username is required")
    String username; // Tên đăng nhập của người dùng
    String password; // Mật khẩu của người dùng
//    @NotBlank(message = "Email is required")
    String email; // Địa chỉ email của người dùng
//    @NotBlank(message = "Name is required")
    String name; // Tên đầy đủ của người dùng
    String phoneNumber; // Số điện thoại của người dùng
    Boolean isActive = true; // Trạng thái hoạt động của người dùng, mặc định là true (hoạt động)

    // 👇 Ghi đè field để format lại khác
    @JsonProperty("createdDate")
    public String getCreatedDateFormatted() {
        return super.getCreatedDate() != null
                ? super.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                : null;
    }

    @JsonProperty("lastModifiedDate")
    public String getLastModifiedDateFormatted() {
        return super.getLastModifiedDate() != null
                ? super.getLastModifiedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                : null;
    }
}
