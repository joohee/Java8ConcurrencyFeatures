package net.joey.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;

import static sun.misc.Version.init;
import static sun.misc.Version.print;

/**
 * Created by Joey on 2016. 8. 22..
 */
@Slf4j
public class ConcurrentMapMain {

    public static void main(String[] args) {
        ConcurrentMapMain main = new ConcurrentMapMain();

        main.runWithConcurrentMap();
        main.runWithConcurrentHashMap();
    }

    public void runWithConcurrentMap() {
        ConcurrentMap<String, String> map = new ConcurrentHashMap();

        map.put("foo", "bar");
        map.put("han", "solo");
        map.put("r2", "d2");
        map.put("c3", "p0");

        map.forEach((key, value) -> log.info("{} = {}", key, value));

        String valueForC3 = map.putIfAbsent("c3", "p1");
        log.info("value for key 'c3' = {}", valueForC3);

        String valueForHi = map.getOrDefault("hi", "there");
        log.info("value for key 'hi' = {}", valueForHi);

        map.replaceAll((key, value) -> "r2".equals(key) ? "d3" : value);
        log.info("value for key 'r2' = {}", map.get("r2"));

        map.compute("foo", (key, value) -> value + value);
        log.info("value for key 'foo' = {}", map.get("foo"));

        map.merge("foo", "boo", (oldVal, newVal) -> newVal + " was " + oldVal);
        log.info("value for key 'foo' = {}", map.get("foo"));

    }

    public void runWithConcurrentHashMap() {

        log.info("Step 1 - map.forEach()");
        log.info("ForkJoinPool.getCommonPoolParallelism() is {}", ForkJoinPool.getCommonPoolParallelism());  // related core count

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("foo", "bar");
        map.put("han", "solo");
        map.put("r2", "d2");
        map.put("c3", "p0");

        map.forEach(1, (key, value) -> log.info("Key: {}, Value: {}, thread: {}", key, value, Thread.currentThread().getName()));

        log.info("Step 2 - map.search()");
        String result = map.search(1, (key, value) -> {
            log.info(Thread.currentThread().getName());
            if ("foo".equals(key)) {
                return value;
            }
            return null;
        });

        log.info("Result: {}", result);

        log.info("Step 3 - map.searchValues()");
        String result2 = map.searchValues(2, value -> {
            log.info(Thread.currentThread().getName());
            if (value.length() > 3) {
                return value;
            }
            return null;
        });
        log.info("Result2: {}", result2);

        log.info("Step 4 - map.reduce()");
        String result3 = map.reduce(1,
                (key, value) -> {
                    log.info("Transform: {}", Thread.currentThread().getName());
                    return key + '=' + value;
                },
                (s1, s2) -> {
                    log.info("Reduce: {}", Thread.currentThread().getName());
                    return s1 + "," + s2;
                });

        log.info("Result: {}", result3);
    }

}
