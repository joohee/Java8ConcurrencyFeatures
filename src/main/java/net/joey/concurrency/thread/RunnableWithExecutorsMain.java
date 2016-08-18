package net.joey.concurrency.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Executor를 이용하여 Thread Pool을 생성합니다.
 * 이때 주의할 점은 ExecutionService는 명시적으로 종료를 시켜줘야 합니다!
 * 그렇지 않으면 계속 새로운 job을 대기하는 상태가 됩니다
 *
 * Created by Joey on 2016. 8. 18..
 */
@Slf4j
public class RunnableWithExecutorsMain {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("Hello, {}", threadName);
        });

        // NOTICE: never stop! so you should stop the job explicitly

        executorService.shutdown();
        try {
            executorService.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (!executorService.isTerminated()) {
                log.info("Cancel non-finished tasks.");
            }
            executorService.shutdownNow();
            log.info("Shutdown finished.");
        }
    }

}
