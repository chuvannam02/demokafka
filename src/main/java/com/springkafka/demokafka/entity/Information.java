package com.springkafka.demokafka.entity;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.entity  *
 * @Author: ChuVanNam
 * @Date: 8/31/2025
 * @Time: 9:15 AM
 */

public class Information {
    private String name = "DefaultName";
    private String address = "DefaultAddress";
    private String phoneNumber = "000-000-0000";
    private Long age = 0L;
    private String email = "namcv12@gmail.com";
    private String jobTitle = "Unknown";
    private String gender = "Unknown";

    // 🔒 instance duy nhất
    private static final Information INSTANCE = new Information();

    private Information() {
    }

    // 🚫 private constructor: không cho new bên ngoài
    private Information(String name, String address, String phoneNumber, Long age, String email, String jobTitle, String gender) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.email = email;
        this.jobTitle = jobTitle;
        this.gender = gender;
    }

    // ✅ Cung cấp method public để lấy instance duy nhất
    public static Information getInstance() {
        return INSTANCE;
    }

    // Getter & Setter
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
