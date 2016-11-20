package net.joey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Optional;

/**
 * Created with Java8ConcurrencyFeatures.
 * User: neigie
 * Date: 2016. 11. 20.
 * Time: 오후 12:21
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class OptionalTest {

    @Test
    public void testOptional() {
        String s1 = "";
        String s2 = null;
        String s3 = "value";

        Optional<String> op1 = Optional.of(s1);
        Optional<String> op2 = Optional.ofNullable(s2);
        Optional<String> op3 = Optional.of(s3);

        System.out.println("s1 with optional: " + op1.orElse("empty value"));
        System.out.println("s2 with optional: " + op2.orElse("null value"));
        System.out.println("s3 with optional: " + op3.orElse("not value"));
    }
}


