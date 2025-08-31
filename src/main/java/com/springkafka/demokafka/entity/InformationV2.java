package com.springkafka.demokafka.entity;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.entity  *
 * @Author: ChuVanNam
 * @Date: 8/31/2025
 * @Time: 10:45 AM
 */

public class InformationV2 {
    private String name = "DefaultName";
    private String address = "DefaultAddress";
    private String phoneNumber = "000-000-0000";
    private Long age = 0L;
    private String email = "namcv12@gmail.com";
    private String jobTitle = "Unknown";
    private String gender = "Unknown";

    // 🔒 instance duy nhất
    private static InformationV2 instance;

    // 🚫 private constructor: không cho new bên ngoài
    private InformationV2() {
    }

    private InformationV2(String name, String address, String phoneNumber, Long age, String email, String jobTitle, String gender) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.email = email;
        this.jobTitle = jobTitle;
        this.gender = gender;
    }

    public static InformationV2 getInstance() {
        if (instance == null) {
            // từ khoá synchronized để đảm bảo chỉ có một thread được phép truy cập vào block này tại một thời điểm
            // giúp tránh việc tạo nhiều instance trong môi trường đa luồng
            // nếu instance đã được khởi tạo rồi thì không cần phải vào synchronized nữa
            // giúp cải thiện hiệu suất khi instance đã được khởi tạo
            // Double-checked locking
            // giúp giảm thiểu overhead của việc sử dụng synchronized
            // bằng cách kiểm tra instance hai lần (trước và sau khi vào synchronized)
            synchronized (InformationV2.class) {
                if (instance == null) {
                    instance = new InformationV2();
                }
            }
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
