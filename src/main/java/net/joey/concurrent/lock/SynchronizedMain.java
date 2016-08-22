package net.joey.concurrent.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static net.joey.concurrent.util.ConcurrentUtils.stop;

/**
 * Thread간 변경 가능한 자원(mutable resource)을 공유할 경우 Race condition 이 발생할 수 있습니다.
 * 해결 방법으로 synchronized 키워드를 사용하여 처리하는 예제입니다.
 * <p>
 * executor는 shutdown을 명시적으로 해주지 않으면 계속 대기상태가 되므로 마지막에 shutdownNow() 메소드를 호출해줍니다.
 * <p>
 * Created by Joey on 2016. 8. 19..
 */
@Slf4j
public class SynchronizedMain {

    int count = 0;
    int countSynchronized = 0;

    public static void main(String[] args) {
        SynchronizedMain main = new SynchronizedMain();

        main.runWithSynchronized();

    }

    public void runWithSynchronized() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10_000).forEach(i -> executor.submit(this::increment));
        log.info("count: {}", count);

        IntStream.range(0, 10_000).forEach(i -> executor.submit(this::incrementSynchronized));
        log.info("countSynchronized: {}", countSynchronized);

        stop(executor);
    }

    void increment() {
        count = count + 1;
//        count++;
//        ++count;
    }

    void incrementSynchronized() {
        synchronized (this) {
            countSynchronized = countSynchronized + 1;
//            countSynchronized++;
        }
    }

}
