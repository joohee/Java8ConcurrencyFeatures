package net.joey.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledExecutorService 예제입니다.
 * 처음에 설정한 initial delay 가 지난 후에 맨 처음 수행됩니다.
 * <p>
 * Created by Joey on 2016. 8. 18..
 */
@Slf4j
public class ScheduledExecutorsMain {

    public static void main(String[] args) {

        ScheduledExecutorsMain main = new ScheduledExecutorsMain();

        main.runWithScheduled();
        main.runWithFixedRate();
        main.runWithRixedDelay();
    }

    public void runWithScheduled() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> log.info("Scheduling: {}", System.nanoTime());
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

        try {
            TimeUnit.MILLISECONDS.sleep(1337);

            long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
            log.info("Remaining Delay: {}", remainingDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * task 수행 시간에 상관없이 delay만큼 task가 수행된다.
     * <p>
     * task 수행 시간을 예측할 수 있을 때 사용한다.
     * <p>
     * ▶ INFO  2016-08-19 15:25:09.664 [pool-2-thread-1] n.j.c.thread.ScheduledExecutorsMain[lambda$runWithFixedRate$1:51] - Scheduling with fixed rate: 57385547432375
     * ▶ INFO  2016-08-19 15:25:11.668 [pool-2-thread-1] n.j.c.thread.ScheduledExecutorsMain[lambda$runWithFixedRate$1:51] - Scheduling with fixed rate: 57387550904811
     * ▶ INFO  2016-08-19 15:25:13.668 [pool-2-thread-1] n.j.c.thread.ScheduledExecutorsMain[lambda$runWithFixedRate$1:51] - Scheduling with fixed rate: 57389551462247
     */
    public void runWithFixedRate() {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                log.info("Scheduling with fixed rate: {}", System.nanoTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        int initialDelay = 2;
        int period = 1;

        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    /**
     * Task 수행시간보다 Scheduler 수행시간이 짧을 경우 task가 수행 완료되지 않아도 또 호출할 수가 있다.
     * 이때 scheduledWithFixedDelay() 를 사용하면 task 완료 후 Delay만큼 지난 후 새로 수행한다
     * <p>
     * 아래 코드의 경우 task가 2초 sleep() 하고 있고 executor는 1초 씩 delay를 갖고 수행하므로
     * 결과적으로 3초에 한 번씩 task가 수행된다.
     * <p>
     * scheduledWithFixedDelay()는 task 수행시간을 예측할 수 없을 때 유용하다.
     * <p>
     * ▶ INFO  2016-08-19 15:25:07.660 [pool-3-thread-1] n.j.c.thread.ScheduledExecutorsMain[lambda$runWithRixedDelay$2:73] - Scheduling with fixed delay: 57383543446000
     * ▶ INFO  2016-08-19 15:25:10.671 [pool-3-thread-1] n.j.c.thread.ScheduledExecutorsMain[lambda$runWithRixedDelay$2:73] - Scheduling with fixed delay: 57386554091660
     * ▶ INFO  2016-08-19 15:25:13.675 [pool-3-thread-1] n.j.c.thread.ScheduledExecutorsMain[lambda$runWithRixedDelay$2:73] - Scheduling with fixed delay: 57389557824926
     */
    public void runWithRixedDelay() {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                log.info("Scheduling with fixed delay: {}", System.nanoTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
    }
}
