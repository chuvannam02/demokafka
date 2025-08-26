package com.springkafka.demokafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
// Kích hoạt hỗ trợ lập trình bất đồng bộ trong Spring Application
// proxyTargetClass = true để sử dụng CGLIB proxy thay vì JDK dynamic proxy
//		Khi bạn đánh dấu một method bằng @Async, Spring sẽ tạo proxy cho bean đó.
//		Theo mặc định, Spring sẽ chọn JDK Dynamic Proxy nếu class implement interface. Do đó, Spring trả về proxy implement interface thay vì chính class NotificationService.
//		Vì vậy, khi bạn inject bằng class cụ thể (NotificationService) thì Spring không tìm thấy bean dạng đó → lỗi.
//@EnableAsync(proxyTargetClass = true)
@EnableAsync
public class DemokafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemokafkaApplication.class, args);
	}

}
