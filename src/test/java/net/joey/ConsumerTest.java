package net.joey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.Consumer;

/**
 * Created with Java8ConcurrencyFeatures.
 * User: neigie
 * Date: 2016. 11. 20.
 * Time: 오후 12:18
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class ConsumerTest {

    @Test
    public void testLambdaExp() {
        Consumer<String> testObject = s -> System.out.println("Test : " + s);

        testObject.accept("Will you accept this?");
    }

}
