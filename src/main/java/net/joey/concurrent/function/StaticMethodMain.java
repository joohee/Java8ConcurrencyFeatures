package net.joey.concurrent.function;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with Java8ConcurrencyFeatures.
 * User: neigie
 * Date: 2016. 9. 19.
 * Time: 10:26
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class StaticMethodMain {

    @FunctionalInterface
    interface MethodInterface<T> {
        void act(T t);

        // default method는 abstract method count에 포함되지 않음
        default void print(T t) {
            log.info("This is default method.");
        }

        static void staticMethod() {
            log.info("This is static method");
        }
    }

    public static void main(String[] args) {
        StaticMethodMain staticMethodMain = new StaticMethodMain();

        MethodInterface.staticMethod();
        staticMethodMain.implementFunctionalInterface();
    }

    private void implementFunctionalInterface() {
        Student student = new Student("abc", 10);
        MethodInterface<Student> methodInterface = (s) -> {
            log.info("what is name?");
            log.info("My name is {}", s.getName());
        };
        methodInterface.act(student);
        methodInterface.print(student);
    }

    @Data
    private class Student {
        String name;
        int age;

        Student(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
