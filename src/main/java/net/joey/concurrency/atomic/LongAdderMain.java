package net.joey.concurrency.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

import static net.joey.concurrency.util.ConcurrentUtils.stop;

/**
 * LongAdder, LongAccumulator 예제 클래스입니다.
 * LongAdder는 내부적으로 변수들을 저장하고 있다가 sum() 이나 sumThenReset() 메소드가 호출 되었을 때 계산합니다.
 * <p>
 * LongAccumulator 는 LongAdder의 general한 버전으로, LongBinaryOperator 를 통해 더 복잡한 연산을 수행할 수 있습니다.
 * LongAccumulator 도 LongAdder와 마찬가지로 내부적으로 변수들을 유지하고 있습니다.
 * <p>
 * Created by Joey on 2016. 8. 19..
 */
@Slf4j
public class LongAdderMain {

    public static void main(String[] args) {

        LongAdderMain main = new LongAdderMain();

        main.runWithLongAdder();
        main.runWithLongAccumulator();
    }

    public void runWithLongAdder() {
        LongAdder adder = new LongAdder();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 1000).forEach(i -> executor.submit(adder::increment));

        stop(executor);

        log.info("value: {}", adder.sumThenReset());
    }

    public void runWithLongAccumulator() {
        LongBinaryOperator op = (x, y) -> 2 * x + y;
        LongAccumulator accumulator = new LongAccumulator(op, 1L);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 10).forEach(i -> executor.submit(() -> accumulator.accumulate(i)));

        stop(executor);

        log.info("value: {}", accumulator.getThenReset());

    }
}
