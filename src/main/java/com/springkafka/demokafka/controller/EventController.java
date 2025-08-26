package com.springkafka.demokafka.controller;

import com.springkafka.demokafka.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.controller  *
 * @Author: ChuVanNam
 * @Date: 8/26/2025
 * @Time: 11:06 AM
 */

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Gọi nhiều tác vụ song song và chờ tất cả hoàn thành
    @GetMapping("/process")
    public String processEvents() throws Exception {
        CompletableFuture<String> event1 = eventService.processEvent("Sự kiện A", 2);
        CompletableFuture<String> event2 = eventService.processEvent("Sự kiện B", 3);
        CompletableFuture<String> event3 = eventService.processEvent("Sự kiện C", 1);

        // Chờ tất cả hoàn thành
        CompletableFuture.allOf(event1, event2, event3).join();

        return "Kết quả: " + event1.get() + ", " + event2.get() + ", " + event3.get();
    }

    // xử lý kết quả ngay khi từng tác vụ hoàn tất
    @GetMapping("/process-fast")
    public String processEventsFast() {
        CompletableFuture<String> event1 = eventService.processEvent("Sự kiện A", 2);
        CompletableFuture<String> event2 = eventService.processEvent("Sự kiện B", 3);
        CompletableFuture<String> event3 = eventService.processEvent("Sự kiện C", 1);

        // Xử lý kết quả ngay khi xong (không chờ tất cả)
        event1.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Callback: " + result);
            } else {
                System.err.println("Lỗi xử lý A: " + ex.getMessage());
            }
        });

        event2.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Callback: " + result);
            } else {
                System.err.println("Lỗi xử lý B: " + ex.getMessage());
            }
        });

        event3.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Callback: " + result);
            } else {
                System.err.println("Lỗi xử lý C: " + ex.getMessage());
            }
        });

        return "Các sự kiện đang được xử lý song song, kết quả sẽ log ra console khi xong!";
    }

    // Kết hợp kết quả của nhiều tác vụ
    @GetMapping("/combine")
    public String combineEvents() throws Exception {
        CompletableFuture<String> event1 = eventService.processEvent("Sự kiện A", 2);
        CompletableFuture<String> event2 = eventService.processEvent("Sự kiện B", 3);

        // Kết hợp kết quả của event1 và event2 khi cả 2 xong
        CompletableFuture<String> combined = event1.thenCombine(event2,
                (resultA, resultB) -> resultA + " + " + resultB);

        // Có thể chain tiếp
        CompletableFuture<String> finalResult = combined.thenApply(r -> "Tổng hợp kết quả: " + r);

        return finalResult.get(); // chờ lấy kết quả cuối cùng
    }

    // Kết hợp nhiều tác vụ hơn
    @GetMapping("/combine-many")
    public String combineManyEvents() throws Exception {
        CompletableFuture<String> event1 = eventService.processEvent("Sự kiện A", 2);
        CompletableFuture<String> event2 = eventService.processEvent("Sự kiện B", 3);
        CompletableFuture<String> event3 = eventService.processEvent("Sự kiện C", 1);
        CompletableFuture<String> event4 = eventService.processEvent("Sự kiện D", 2);

        // Gom nhiều CompletableFuture bằng cách chain thenCombine
        CompletableFuture<String> combined = event1
                .thenCombine(event2, (a, b) -> a + " + " + b)
                .thenCombine(event3, (ab, c) -> ab + " + " + c)
                .thenCombine(event4, (abc, d) -> abc + " + " + d);

        // Thêm bước xử lý kết quả cuối cùng
        CompletableFuture<String> finalResult = combined.thenApply(r -> "Kết quả tổng hợp: " + r);

        return finalResult.get(); // chờ kết quả cuối
    }
}
