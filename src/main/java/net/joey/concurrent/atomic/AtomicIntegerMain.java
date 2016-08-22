package net.joey.concurrent.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static net.joey.concurrent.util.ConcurrentUtils.stop;

/**
 *
 * AtomicInteger 클래스의 연산은 atomic합니다. 고로 Thread-safe 합니다.
 * AtomicInteger.updateAndGet() 메소드를 통해 update 식을 lambda expression으로 정의할 수 있습니다.
 *
 * Created by Joey on 2016. 8. 19..
 */
@Slf4j
public class AtomicIntegerMain {

    public static void main(String[] args) {
        AtomicIntegerMain main = new AtomicIntegerMain();

        main.runWithAtomic();
        main.runWithUpdateAndGet();
    }

    public void runWithAtomic() {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000).forEach(i -> executor.submit(atomicInteger::incrementAndGet));

        stop(executor);

        log.info("value: {}", atomicInteger.get());
    }

    public void runWithUpdateAndGet() {
        AtomicInteger atomicInteger = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000).forEach(i -> {
            Runnable task = () -> atomicInteger.updateAndGet(n -> n + 2);
            executor.submit(task);
        });

        stop(executor);

        log.info("value: {}", atomicInteger.get());
    }
}

