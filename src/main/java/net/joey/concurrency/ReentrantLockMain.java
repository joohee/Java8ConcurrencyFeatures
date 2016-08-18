package net.joey.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * ReentrantLock을 이용하여 lock을 최소한으로 설정할 수 있도록 합니다.
 * 이때 주의할 점은 finally에서 반드시 unlock() 메소드를 호출해줘야 합니다.
 * 그렇지 않으면 lock을 설정한 후 Exception이 발생하면 lock은 영원히 풀리지 않습니다.
 *
 * Created by Joey on 2016. 8. 18..
 */
@Slf4j
public class ReentrantLockMain {

    public static void main(String[] args) {
        ReentrantLockMain lockMain = new ReentrantLockMain();

        lockMain.exampleReentrantLock();
    }

    public void exampleReentrantLock() {
        final List<String> list = new ArrayList<>();
        final ReentrantLock lock = new ReentrantLock();

        IntStream.range(0, 2).forEach(i -> {
            Thread producer = new Thread(() -> {
                while (true) {
                    try {
                        lock.lock();
                        IntStream.range(0, 10).forEach(item -> list.add(String.valueOf(item)));

                    } finally {
                        lock.unlock();
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            producer.start();
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    lock.lock();
                    StringBuffer sb = new StringBuffer();
                    list.forEach(item -> {
                        sb.append(item).append(" ");
                    });
                    log.info(sb.toString());

                } finally {
                    lock.unlock();
                    list.clear();
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        consumer.start();


    }
}
