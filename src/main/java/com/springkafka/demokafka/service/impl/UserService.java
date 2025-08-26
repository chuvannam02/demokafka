package com.springkafka.demokafka.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springkafka.demokafka.dto.in.user.UserDTO;
import com.springkafka.demokafka.dto.in.user.UserListFilterDTO;
import com.springkafka.demokafka.entity.User;
import com.springkafka.demokafka.exception.BadRequestException;
import com.springkafka.demokafka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.service.impl  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 10:11 AM
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserInterface {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper;


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void createUser(UserDTO userDTO) {
        log.info("Creating new user with DTO: {}", userDTO);
        if (userRepository.countByUsername(userDTO.getUsername()) > 0) {
            throw new IllegalArgumentException("Username already exists: " + userDTO.getUsername());
        }

        if (userRepository.countByEmail(userDTO.getEmail()) > 0) {
            throw new IllegalArgumentException("Email already exists: " + userDTO.getEmail());
        }

        // Encode the password before saving
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        try {
            // Convert UserDTO to User entity if needed
             User user = objectMapper.convertValue(userDTO, User.class);
             userRepository.save(user);
            log.info("User created successfully: {}", userDTO.getUsername());
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());
            throw new BadRequestException("Failed to create user: " + e.getMessage());
        }
    }

    @Override
    public void updateUser(UserDTO userDTO) {

    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return null;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public Page<UserDTO> getAllUsers(UserListFilterDTO userListFilterDTO) {
        int page = Optional.ofNullable(userListFilterDTO.getPage()).orElse(1) - 1; // default page = 1
        int size = Optional.ofNullable(userListFilterDTO.getSize()).orElse(10);     // default size = 10

        Sort sort = Sort.unsorted();
        if (userListFilterDTO.getPropertiesSort() != null && userListFilterDTO.getSortOrder() != null) {
            sort = "asc".equalsIgnoreCase(userListFilterDTO.getSortOrder())
                    ? Sort.by(userListFilterDTO.getPropertiesSort()).ascending()
                    : Sort.by(userListFilterDTO.getPropertiesSort()).descending();
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<User> userPage = userRepository.findAllUsersByKeyword(userListFilterDTO.getKeyword(), pageRequest);

        // Sử dụng ObjectMapper để map entity sang DTO
        Page<UserDTO> dtoPage = userPage.map(user -> objectMapper.convertValue(user, UserDTO.class));

        return dtoPage;
    }
}
