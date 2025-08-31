package com.springkafka.demokafka.controller;

import com.springkafka.demokafka.entity.Animal;
import com.springkafka.demokafka.entity.InformationV2;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.controller  *
 * @Author: ChuVanNam
 * @Date: 8/30/2025
 * @Time: 1:19 PM
 */

public class Test {
    public static void main(String[] args) {
        // Tạo đối tượng Animal sử dụng hàm tạo không đối số
//        Animal animal = new Animal();
        // test access modifier private
//        System.out.println(animal.name);
        // Tạo đối tượng Animal sử dụng hàm tạo có đối số
        Animal animal1 = new Animal("Leo", 3L, "Lion");
        Animal animal2 = new Animal("Leo", 3L, "Lion");
        Animal animal5 = new Animal("Leo", 4L, "Lion");
        Animal animal3 = new Animal("Milo", 2L, "Cat");
        Animal animal4 = new Animal("Bella", 4L, "Dog");

        // So sánh hai đối tượng
        System.out.println("animal1 equals animal2: " + animal1.equals(animal2)); // true
        System.out.println("animal1 hashCode: " + animal1.hashCode());
        System.out.println("animal2 hashCode: " + animal2.hashCode()); // giống nhau

//        List<Animal> animals = List.of(animal1, animal2, animal3, animal4);
        List<Animal> animals = Arrays.asList(animal1, animal2, animal3, animal4, animal5);
//        Collections.sort(animals);
//        animals.forEach(a -> System.out.println(a.getName() + " - " + a.getAge() + " - " + a.getType()));
        Comparator<Animal> sortByName = new Comparator<Animal>() {
            @Override
            public int compare(Animal a1, Animal a2) {
                return a1.getName().compareToIgnoreCase(a2.getName());
            }
        };
        // => sủ dụng lambda expression
        Comparator<Animal> sortByNameLamba = (a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName());
        // sử dụng method reference
        // String.CASE_INSENSITIVE_ORDER: để so sánh không phân biệt chữ hoa thường
        // Animal::getName: tham chiếu đến phương thức getName của lớp Animal
        // Comparator.comparing: tạo Comparator dựa trên giá trị trả về của phương thức getName
        // Kết quả là một Comparator sắp xếp các đối tượng Animal theo tên không phân biệt chữ hoa thường
        // nếu muốn sắp xếp theo tuổi thì thay getName thành getAge
        // nếu muốn sắp xếp theo tuổi giảm dần thì thêm .reversed() vào cuối
        // Comparator<Animal> sortByAgeDesc = Comparator.comparing(Animal::getAge).reversed();
        // Nếu muốn sắp xếp theo nhiều tiêu chí, ví dụ: trước tiên theo tên, sau đó theo tuổi
        // method comparing() truyền vào một hàm lấy giá trị để so sánh
        // Comparator.comparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> keyComparator)
        // Function là một functional interface đại diện cho một hàm nhận một đối số và trả về một giá trị
        // Comparator là một interface để so sánh hai đối tượng
        Comparator<Animal> sortByNameMethodRef = Comparator.comparing(Animal::getName, String.CASE_INSENSITIVE_ORDER);

        // Sắp xếp theo nhiều tiêu chí (tên, tuổi)
        Comparator<Animal> sortByNameThenAge = Comparator.comparing(Animal::getName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Animal::getAge);
        Collections.sort(animals, sortByNameThenAge);
        animals.forEach(a -> System.out.println(a.getName() + " - " + a.getAge() + " - " + a.getType()));

//        Collections.sort(animals, sortByName);
//        animals.forEach(a -> System.out.println(a.getName() + " - " + a.getAge() + " - " + a.getType()));

//        Animal animal = Animal.builder()
//                .name("Tom")
//                .age(5L)
//                .build();
//        System.out.println(animal);

        InformationV2 a3 = InformationV2.getInstance();
        InformationV2 a4 = InformationV2.getInstance();

        System.out.println(a3.getName()); // Dog
        System.out.println(a4.getName()); // Dog (vẫn cùng 1 instance)
        System.out.println(a3 == a4);     // true
    }
}
