# 🔐 Kafka Security — ACL & SASL Configuration Guide

## 🧱 1️⃣ Access Control List (ACL) là gì?

**ACL (Access Control List)** trong **Kafka** là danh sách phân quyền truy cập — quy định **ai được làm gì với tài nguyên nào**.

Nói cách khác, bạn có thể kiểm soát chi tiết:

- 👤 **Ai** (user hoặc principal nào)
- ⚙️ **Được làm hành động nào** (read, write, create, describe, delete, …)
- 📦 **Trên tài nguyên nào** (topic, consumer group, cluster, transactionalId, …)

---

## 🔐 2️⃣ Tại sao cần ACL trong Kafka?

Nếu **không bật ACL**, bất kỳ client nào kết nối tới Kafka (qua `SASL/PLAINTEXT` hoặc `PLAINTEXT`) đều có thể:

- 📥 Đọc toàn bộ topic
- 📤 Ghi đè dữ liệu
- 🗑️ Xóa nhầm topic

Điều này **rất nguy hiểm** trong môi trường production, ví dụ:

- Một service lỗi có thể ghi nhầm dữ liệu vào topic quan trọng.
- Một user test có thể xóa nhầm topic hoặc đọc log nhạy cảm.
- Một consumer không được phép vẫn có thể đọc stream riêng tư.

👉 **ACL** giải quyết vấn đề này bằng cách yêu cầu Kafka Authorizer xác minh quyền trước mỗi hành động.

---

## ⚙️ 3️⃣ Cấu hình bật ACL trong Kafka Broker

Trong `docker-compose.yml` của Kafka Broker:

```yaml
environment:
  KAFKA_AUTHORIZER_CLASS_NAME: kafka.security.authorizer.AclAuthorizer
  KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND: "false" # Bắt buộc có ACL mới được phép
```

🧩 4️⃣ Cách thêm ACL

Giả sử bạn có user "admin" và "user1".
Bạn muốn user1 chỉ được read/write vào topic "orders":
```
# Cho phép user1 ghi vào topic orders
kafka-acls --authorizer-properties zookeeper.connect=zookeeper:2181 \
--add --allow-principal User:user1 --operation Write --topic orders

# Cho phép user1 đọc topic orders
kafka-acls --authorizer-properties zookeeper.connect=zookeeper:2181 \
--add --allow-principal User:user1 --operation Read --topic orders
```

Kiểm tra:
```
kafka-acls --authorizer-properties zookeeper.connect=zookeeper:2181 --list
```
🧠 5️⃣ Các loại tài nguyên mà ACL có thể kiểm soát
Loại tài nguyên	Ví dụ	Mục đích
Topic	orders, transactions	Cho phép đọc, ghi, xóa, tạo topic
Group	order-consumer-group	Cho phép join group, commit offset
Cluster	kafka-cluster	Cho phép tạo/xóa topic toàn hệ thống
TransactionalId	tx-1	Cho phép dùng giao dịch Kafka
DelegationToken	token-123	Dùng cho cơ chế ủy quyền

💡 6️⃣ Nguyên tắc vận hành tốt

Tạo một user “admin” có full quyền để quản trị (Describe, Create, Delete, v.v.)

Mỗi ứng dụng nên có user riêng biệt với quyền hạn giới hạn:

producer-user → chỉ được Write vào 1 số topic

consumer-user → chỉ được Read 1 số topic

Khi test local, bạn có thể bật:
```
KAFKA_ALLOW_EVERYONE_IF_NO_ACL_FOUND: "true"
```
nhưng khi triển khai thật → phải chuyển sang false.

📚 7️⃣ Minh họa nhanh (mô hình tư duy)
```
[Kafka Broker]
|
┌───────────────────┐
│ ACL kiểm tra quyền│
└───────────────────┘
↑                 ↑
Producer(user1)     Consumer(user2)
|                   |
└──→ topic "orders" ←──┘
```

Nếu user1 có quyền Write vào orders → OK ✅
Nếu user2 chưa có quyền Read → bị từ chối ❌

Rất hay 👍 — nếu bạn muốn bảo mật Kafka Producer/Consumer bằng SASL, thì đó là hướng chuẩn và được khuyến nghị khi triển khai Kafka trong môi trường microservices hoặc cloud.
Dưới đây là hướng dẫn chi tiết để bạn chuyển từ kết nối thường sang kết nối bảo mật SASL/PLAIN hoặc SASL/SCRAM, kèm cấu hình Spring Boot tương ứng.

🧩 1. Tổng quan SASL trong Kafka

SASL (Simple Authentication and Security Layer) là cơ chế giúp Kafka xác thực client (producer/consumer) khi kết nối với broker.

Các cơ chế phổ biến:

Cơ chế	Mô tả	Bảo mật
SASL/PLAIN	Username/password gửi plaintext qua TLS	Trung bình (cần TLS để an toàn)
SASL/SCRAM-SHA-256/512	Xác thực bằng hash (an toàn hơn)	Cao
SASL/GSSAPI	Dùng Kerberos	Rất cao (thường dùng trong doanh nghiệp)

⚙️ 2. Cấu hình Kafka Broker (ví dụ docker-compose)
```
version: '3.8'
services:
zookeeper:
image: confluentinc/cp-zookeeper:7.7.0
environment:
ZOOKEEPER_CLIENT_PORT: 2181

kafka:
image: confluentinc/cp-kafka:7.7.0
ports:
- "9093:9093"
environment:
KAFKA_BROKER_ID: 1
KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
KAFKA_LISTENERS: SASL_PLAINTEXT://0.0.0.0:9093
KAFKA_ADVERTISED_LISTENERS: SASL_PLAINTEXT://kafka:9093
KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: SASL_PLAINTEXT:SASL_PLAINTEXT
KAFKA_INTER_BROKER_LISTENER_NAME: SASL_PLAINTEXT

      # Cấu hình SASL
      KAFKA_SASL_ENABLED_MECHANISMS: PLAIN
      KAFKA_SASL_MECHANISM_INTER_BROKER_PROTOCOL: PLAIN
      KAFKA_OPTS: "-Djava.security.auth.login.config=/etc/kafka/server-jaas.conf"

    volumes:
      - ./server-jaas.conf:/etc/kafka/server-jaas.conf
```
📄 3. File server-jaas.conf (Kafka Broker)
```
KafkaServer {
  org.apache.kafka.common.security.plain.PlainLoginModule required
  username="admin"
  password="admin-secret"
  user_admin="admin-secret"
  user_user1="user1-secret";
};
```

🧠 4. Cấu hình Spring Boot (Producer/Consumer)
application.yml
```
spring:
  kafka:
    bootstrap-servers: kafka:9093
    properties:
      security.protocol: SASL_PLAINTEXT
      sasl.mechanism: PLAIN
      sasl.jaas.config: org.apache.kafka.common.security.plain.PlainLoginModule required \
        username="user1" \
        password="user1-secret";
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    consumer:
      group-id: my-group
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
```

🧩 5. Ví dụ Java Config (nếu muốn động theo ENV)
Bạn có thể viết riêng KafkaSecurityConfig.java:
```java
@Configuration
public class KafkaSecurityConfig {

    @Bean
    public Map<String, Object> producerConfigs(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
            @Value("${KAFKA_USERNAME}") String username,
            @Value("${KAFKA_PASSWORD}") String password
    ) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        props.put(SaslConfigs.SASL_JAAS_CONFIG,
                String.format("org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";", username, password)
        );
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
}

```

🛡️ 6. Dùng TLS cho bảo mật toàn diện

Nếu muốn mã hóa dữ liệu truyền (chứ không chỉ xác thực), hãy bật:
```
security.protocol: SASL_SSL
```
✅ 7. Kết luận
Thành phần	Bảo mật	Mức độ phức tạp	Gợi ý
PLAINTEXT	❌ Không bảo mật	Thấp	Chỉ dùng local dev
SASL/PLAIN	✅ Có xác thực	Trung bình	Dùng khi có TLS
SASL/SCRAM	✅ An toàn hơn	Trung bình	Dùng production
SASL_SSL	🔒 Xác thực + Mã hóa	Cao	Production khuyến nghị
