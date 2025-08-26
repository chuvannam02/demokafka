package com.springkafka.demokafka.controller;

import com.springkafka.demokafka.service.NotificationInterface;
import com.springkafka.demokafka.service.impl.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.controller  *
 * @Author: ChuVanNam
 * @Date: 8/26/2025
 * @Time: 10:52 AM
 */

@RestController
@RequestMapping("/notify")
@RequiredArgsConstructor
public class NotificationController {

//    private final NotificationService notificationService;
    private final NotificationInterface notificationService;

    @GetMapping("/{user}")
    public String notifyUser(@PathVariable String user) {
        notificationService.sendNotification(user);    // chạy async
        notificationService.logAction(user);    // chạy async
        return "Yêu cầu xử lý cho " + user + " đang chạy song song!";
    }
}
