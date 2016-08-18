package net.joey.concurrency.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Runnable의 다른 형태인 Callable 에 대한 예제입니다
 * <p>
 * Callable - Runnable과 다르게 return value가 있습니다.
 * <p>
 * Q. 그러나 Executor를 통해서 수행하였을 경우 결과값은 어떻게 얻어올까요?
 * A. Executors.submit()의 return type은 Future입니다.
 * Future.get()을 통해 결과값을 받아올 수 있습니다.
 * <p>
 * Created by Joey on 2016. 8. 18..
 */
@Slf4j
public class CallableAndFutureMain {

    public static void main(String[] args) {

        CallableAndFutureMain main = new CallableAndFutureMain();
        main.runCallableAndReturnToFuture();
        log.info("---------------------------");
        main.runWithTimeout();

    }

    public void runCallableAndReturnToFuture() {
        Callable<Integer> task = () -> {
            log.info("Start task.");
            TimeUnit.SECONDS.sleep(1);
            return Integer.MAX_VALUE;
        };

        try {
            int result = task.call();
            log.info("result of callable : {}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Q. ExecutorService.submit()은 결과값을 반환하지 않는다. 그렇다면 어떻게 결과를 받는가?
        // A. Future.get()

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(task);
        log.info("Future done? {}", future.isDone());

        try {
            Integer resultOfFuture = future.get();

            log.info("Future done? {}", future.isDone());
            log.info("result of Future: {}", resultOfFuture);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * TimeoutException 발생 예제.
     */
    public void runWithTimeout() {

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return Integer.MAX_VALUE;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });

        try {
            future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

}
