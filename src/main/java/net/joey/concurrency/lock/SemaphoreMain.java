package net.joey.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 일반적인 Lock은 exclusive 하게 접근하게 하는데 반해,
 * Semaphore 는 permit 숫자를 정해서 제한된 갯수의 thread 가 동시에 접근하도록 할 수 있습니다.
 *
 * Created by Joey on 2016. 8. 19..
 */
@Slf4j
public class SemaphoreMain {

    public static void main(String[] args) {
        SemaphoreMain main = new SemaphoreMain();

        main.runWithSemaphore();

    }

    public void runWithSemaphore() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Semaphore semaphore = new Semaphore(5);

        Runnable longRunningTask = () -> {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    log.info("Semaphore Acquired.");
                    Thread.sleep(3000);
                } else {
                    log.error("Could not acquire semaphore.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }
        };

        IntStream.range(0, 10).forEach(i -> executor.submit(longRunningTask));

    }

}
