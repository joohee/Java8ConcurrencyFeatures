package net.joey.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * CountDownLatch 를 이용하여 countDown이 소진 될 때 까지 main thread 가 await() 상태에 걸려 있는 것을 보여주는 예제입니다.
 * Created by Joey on 2016. 8. 18..
 */
@Slf4j
public class CountDownLatchMain {


    public static void main(String[] args) {
        CountDownLatchMain countDownLatchMain = new CountDownLatchMain();
        countDownLatchMain.runCountDownLatch();
    }

    public void runCountDownLatch() {
        final CountDownLatch latch = new CountDownLatch(10);

        IntStream.range(0, 10).forEach(i -> {
            Thread t = new Thread(() -> {
                log.info("{} is started.", Thread.currentThread().getName());
                synchronized (latch) {
                    log.info("Remain count of countdown: " + latch.getCount());
                    latch.countDown();
                }
            });
            t.start();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Main thread is done.");
    }
}
