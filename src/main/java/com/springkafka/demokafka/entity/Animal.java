package com.springkafka.demokafka.entity;

import lombok.Builder;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.entity  *
 * @Author: ChuVanNam
 * @Date: 8/30/2025
 * @Time: 1:21 PM
 */

@Builder
public class Animal implements Comparable<Animal>{

    private String name = "DefaultName";

    private Long age = 0L;

    private String type = "Unknown";

    // Hàm tạo không đối số
    public Animal() {

    }

    // Hàm tạo có đối số
    public Animal(String name, Long age, String type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }

    // Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Để so sánh hai đối tượng Animal
    // Thì cần override equals() và hashCode()
    // Trong đó equals() so sánh các thuộc tính của đối tượng
    // hashCode() trả về mã băm dựa trên các thuộc tính đó
    // Điều này giúp đảm bảo rằng hai đối tượng với cùng giá trị thuộc tính được coi là bằng nhau
    // và có cùng mã băm
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Animal animal = (Animal) o;

        if (!name.equals(animal.name)) return false;
        if (!age.equals(animal.age)) return false;
        return type.equals(animal.type);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + age.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public int compareTo(Animal otherAnimal) {
        return this.age.compareTo(otherAnimal.age); // So sánh theo tuổi
    }
}
