package net.joey.concurrent.function;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

/**
 * java.util.concurrent.function.Predicate 인터페이스 예제입니다.
 * Predicate는 @FunctionalInterface annotation이 붙어있고 test()메소드는 1 parameter, boolean return 을 지원합니다.
 * <p>
 * Created by Joey on 2016. 8. 23..
 */
@Slf4j
public class PredicateMain {

    public static void main(String[] args) {
        PredicateMain main = new PredicateMain();

        main.runWithPredicate();

    }

    private void runWithPredicate() {

        Student student1 = new Student("Joey", 12345678);
        Student student2 = new Student("Paul", 1);

        Predicate<Student> predicate = (p) -> {
            if (p.num > 10) {
                return true;
            }
            return false;
        };

        log.info("test for student1: {}", predicate.test(student1));
        log.info("test for student2: {}", predicate.test(student2));
    }

    @Data
    private class Student {
        private String name;
        private int num;

        Student(String name, int num) {
            this.name = name;
            this.num = num;
        }

    }
}
