package com.springkafka.demokafka.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.springkafka.demokafka.AOP.AdditionalRequestTrack;
import com.springkafka.demokafka.AOP.TrackExecutionTime;
import com.springkafka.demokafka.dto.in.user.UserDTO;
import com.springkafka.demokafka.dto.in.user.UserListFilterDTO;
import com.springkafka.demokafka.dto.out.response.BaseResponse;
import com.springkafka.demokafka.dto.view.Views;
import com.springkafka.demokafka.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.controller  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 9:57 AM
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    /**
     * Create a new user
     *
     * @param userDTO User data transfer object
     * @return ResponseEntity with BaseResponse
     */

    @PostMapping("/user/create")
    @TrackExecutionTime
    @AdditionalRequestTrack
    public ResponseEntity<BaseResponse> createUser(@RequestBody @Valid UserDTO userDTO) {
        BaseResponse response = new BaseResponse();
        userService.createUser(userDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/user/get-all")
    @AdditionalRequestTrack
    @JsonView(Views.Public.class)
    public ResponseEntity<BaseResponse> getAllUsers(@ModelAttribute UserListFilterDTO filter) {
        BaseResponse response = new BaseResponse();
        response.setData(userService.getAllUsers(filter));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
