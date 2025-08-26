package com.springkafka.demokafka.service.impl;

import com.springkafka.demokafka.dto.in.user.UserDTO;
import com.springkafka.demokafka.dto.in.user.UserListFilterDTO;
import com.springkafka.demokafka.entity.User;
import org.springframework.data.domain.Page;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.service.impl  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 9:57 AM
 */

public interface UserInterface {
    void createUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    UserDTO getUserById(Long id);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);
    Page<UserDTO> getAllUsers(UserListFilterDTO userListFilterDTO);
}
