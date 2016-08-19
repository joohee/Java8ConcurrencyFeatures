package net.joey.concurrency.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 중복되는 stop/sleep 메소드를 구현해 놓은 클래스입니다
 * <p>
 * Created by Joey on 2016. 8. 19..
 */
@Slf4j
public class ConcurrentUtils {

    public static void stop(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("termination interrupted.");
        } finally {
            if (!executor.isTerminated()) {
                log.error("Killing non-finished tasks.");
            }
            executor.shutdownNow();
        }
    }

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
