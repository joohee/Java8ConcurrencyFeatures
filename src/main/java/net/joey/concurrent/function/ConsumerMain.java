package net.joey.concurrent.function;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * java.util.concurrent.function.Consumer 인터페이스 사용 예제입니다.
 * Consumer는 1개의 parameter를 받고 return 값이 없습니다. (void)
 *
 * Created by Jjoey on 2016. 8. 23..
 */
@Slf4j
public class ConsumerMain {

    public static void main(String[] args) {
        ConsumerMain main = new ConsumerMain();
        main.runWithConsumer();

    }

    public void runWithConsumer() {
        Consumer<Student> consumer = (s) -> log.info("Name: {}, gpa: {}", s.getName(), s.getGpa());

        Student s = new Student("Joey", 1.0);
        consumer.accept(s);
    }

    @Data
    private class Student {
        private String name;
        private double gpa;

        Student(String name, double gpa) {
            this.name = name;
            this.gpa = gpa;
        }
    }
}
