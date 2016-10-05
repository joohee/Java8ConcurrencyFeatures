package net.joey.concurrent.function;

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

    interface MethodInterface {
        default void print() {
            log.info("This is default method.");
        }

        static void staticMethod() {
            log.info("This is static method");
        }
    }

    public static void main(String[] args) {

        MethodInterface.staticMethod();
    }
}
