import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static sun.misc.Version.print;

/**
 * Created by Joey on 2016. 8. 23..
 */
@Slf4j
@RunWith(JUnit4.class)
public class CallByValueTest {

    @Test
    public void testCallByValue() {

        Counter counter = new Counter();
        log.info("counter: {}", counter.count);
        increaseCounter(counter);
        log.info("counter: {}", counter.count);
        changeObject(counter);
        log.info("counter: {}", counter.count);
    }

    private void changeObject(Counter counter) {
        counter = new Counter();                    // 객체 변경은 여기서만 유효함.
        log.info("counter: {}", counter.count);
    }

    private void increaseCounter(Counter counter) {
        counter.count++;
    }

    @Data
    public class Counter {
        public int count = 0;
    }

    @Test
    public void testCallByValue2() {
        Balloon redBalloon = new Balloon("red");
        Balloon blueBalloon = new Balloon("blue");

        swap(redBalloon, blueBalloon);
        log.info("redBalloon color: {}", redBalloon.getColor());
        log.info("blueBalloon color: {}", blueBalloon.getColor());

        foo(blueBalloon);
        log.info("blueBalloon color: {}", blueBalloon.getColor());
    }

    private void foo(Balloon ballon) {
        ballon.setColor("red");
        ballon = new Balloon("green");
        ballon.setColor("blue");
    }

    private void swap(Balloon o1, Balloon o2) {
        Balloon temp = o1;
        o1 = o2;
        o2 = temp;
    }

    @Data
    private class Balloon {
        String color = null;

        Balloon(String color) {
            this.color = color;
        }
    }
}
