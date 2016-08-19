package net.joey.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

import static net.joey.concurrency.util.ConcurrentUtils.stop;

/**
 * Created by Joey on 2016. 8. 19..
 */
@Slf4j
public class StampedLockMain {

    public static void main(String[] args) {
        StampedLockMain main = new StampedLockMain();

        main.runWithStampedLock();
        main.runWithOptimisticLock();
        main.runWithConvertLock();

    }

    public void runWithStampedLock() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            long stamp = lock.writeLock();
            try {
                Thread.sleep(1);
                map.put("foo", "bar");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockWrite(stamp);
            }
        });

        Runnable readTask = () -> {
            long stamp = lock.readLock();

            try {
                log.info(map.get("foo"));
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockRead(stamp);
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);

        stop(executor);

    }


    /**
     * Optimistic Lock을 획득한 후 다른 thread에서 lock 을 획득하면 lock은 invalidate되고 그 상태가 유지된다.
     * <p>
     * 이때 조심할 것은, lock.writeLock() 을 할 땐 lock을 얻을 때 까지 blocking이 된다.
     */
    public void runWithOptimisticLock() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            long stamp = lock.tryOptimisticRead();               // non-blocking

            try {
                log.info("Optimistic Lock Valid: {}", lock.validate(stamp));
                Thread.sleep(1000);
                log.info("Optimistic Lock Valid: {}", lock.validate(stamp));
                Thread.sleep(2000);
                log.info("Optimistic Lock Valid: {}", lock.validate(stamp));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockRead(stamp);
            }

        });

        executor.submit(() -> {
            long stamp = lock.writeLock();

            try {
                log.info("Write Lock Acquired...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlockWrite(stamp);
                log.info("Write Done.");
            }
        });

        stop(executor);

    }

    public void runWithConvertLock() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLock lock = new StampedLock();

        executor.submit(() -> {
            int count = 0;
            long stamp = lock.readLock();
            try {
                if (count == 0) {
                    stamp = lock.tryConvertToWriteLock(stamp);
                    if (stamp == 0L) {
                        log.error("Could not convert to write lock.");
                        stamp = lock.writeLock();       // blocking
                    }
                    count = 23;
                }
                log.info("Count: {}", count);
            } finally {
                lock.unlock(stamp);
            }
        });

        stop(executor);
    }
}
