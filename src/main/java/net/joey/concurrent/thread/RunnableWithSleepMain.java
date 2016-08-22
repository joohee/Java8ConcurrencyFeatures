package net.joey.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Runnable 안에서 TimeUnit.SECONDS.sleep()을 설정합니다.
 * main thread가 종료된 후에 결과가 찍히는 것을 볼 수 있습니다.
 *
 * Created by Joey on 2016. 8. 18..
 */
@Slf4j
public class RunnableWithSleepMain {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            try {
                String name = Thread.currentThread().getName();
                log.info("Step 1 {}", name);
                TimeUnit.SECONDS.sleep(1);
                log.info("Step 2 {}", name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t = new Thread(runnable);
        t.start();
    }
}
