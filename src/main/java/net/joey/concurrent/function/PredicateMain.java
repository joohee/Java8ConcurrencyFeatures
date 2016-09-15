package net.joey.concurrent.function;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        log.info("-------------------------------");
        main.runWithPredicates();

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

    private void runWithPredicates() {

        List<Student> students = IntStream.range(0, 100).mapToObj(i -> {
            Student student = new Student();
            student.setName(RandomStringUtils.randomAlphabetic(10));
            student.setNum(RandomUtils.nextInt(0, 100));
            return student;
        }).collect(Collectors.toList());

        Predicate<Student> over40Num = (s) -> s.getNum() > 40;
        Predicate<Student> startWithAlphabet = (s) -> StringUtils.startsWithAny(s.getName(), "a", "b", "c");

        students.stream().filter(over40Num.and(startWithAlphabet)).forEach(student -> log.info("name: {}, num: {}", student.getName(), student.getNum()));
    }

    @Data
    private class Student {
        private String name;
        private int num;

        Student() {
        }

        Student(String name, int num) {
            this.name = name;
            this.num = num;
        }

    }
}
