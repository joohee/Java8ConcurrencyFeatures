package net.joey.concurrent.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * ReentrantReadWriteLock을 사용하여 readLock/writeLock 을 분리 사용할 수 있는 예제입니다.
 *
 * @see ReentrantLockMain
 * Created by Joey on 2016. 8. 18..
 */
@Slf4j
public class ReentrantReadWriteLockMain {

    public static void main(String[] args) {
        ReentrantReadWriteLockMain lockMain = new ReentrantReadWriteLockMain();

        lockMain.exampleReentrantReadWriteLock();
    }

    public void exampleReentrantReadWriteLock() {
        List<String> list = new ArrayList<>();

        final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        IntStream.range(0, 2).forEach(i -> {
            Thread producer = new Thread(() -> {
                while (true) {
                    try {
                        writeLock.lock();
                        IntStream.range(0, 10).forEach(item -> {
                            list.add(String.valueOf(item));
                        });
                    } finally {
                        writeLock.unlock();
                    }

                    try {
                        TimeUnit.SECONDS.sleep(2);
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
                    readLock.lock();
                    StringBuffer sb = new StringBuffer();
                    list.forEach(item -> {
                        sb.append(item).append(' ');
                    });
                    log.info(sb.toString());
                } finally {
                    list.clear();
                    readLock.unlock();
                }
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        consumer.start();
    }
}
