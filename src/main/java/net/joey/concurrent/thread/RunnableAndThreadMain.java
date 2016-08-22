package net.joey.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * Runnable 구현체와 Thread 내부에서 각각 name을 찍어봅니다.
 * Created by Joey on 2016. 8. 18..
 */
@Slf4j
public class RunnableAndThreadMain {

    public static void main (String[] args) {
        Runnable runnable = () -> log.info("name: {}", Thread.currentThread().getName());
        runnable.run();

        Thread t = new Thread(runnable);
        t.start();

        log.info("Done.");
    }
}
