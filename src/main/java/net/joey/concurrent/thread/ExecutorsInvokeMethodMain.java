package net.joey.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 모든 Callable을 한꺼번에 받으려면, ExecutorService.invokeAll() 메소드를 사용합니다.
 * invokeAll()
 * <p>
 * 여러 Callable 중 가장 빠른 것만 리턴 -> invokeAny()
 * <p>
 * Created by Joey on 2016. 8. 18..
 */
@Slf4j
public class ExecutorsInvokeMethodMain {


    public static void main(String[] args) {
        ExecutorsInvokeMethodMain main = new ExecutorsInvokeMethodMain();

        main.runWithInvokeAll();
        log.info("-----------------------");
        main.runWithInvokeAny();

    }

    public void runWithInvokeAll() {
        ExecutorService executors = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(() -> "task1", () -> "task2", () -> "task3");

        try {
            executors.invokeAll(callables).stream().map(future -> {
                try {
                    return future.get();
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }).forEach(log::info);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runWithInvokeAny() {
        ExecutorService executor = Executors.newWorkStealingPool();
        List<Callable<String>> callables = Arrays.asList(
            callable("task1", 2),
            callable("task2", 1),
            callable("task3", 3)
        );

        try {
            String result = executor.invokeAny(callables);
            log.info("result: {}", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Callable callable(String result, long sleepSeconds) {
        return () -> {
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

}
