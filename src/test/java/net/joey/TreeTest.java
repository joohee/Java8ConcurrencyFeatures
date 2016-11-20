package net.joey;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created with Java8ConcurrencyFeatures.
 * User: neigie
 * Date: 2016. 9. 23.
 * Time: 10:30
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RunWith(JUnit4.class)
public class TreeTest {

    @Test(expected = ClassCastException.class)
    public void testTreeSet() {
        TreeSet treeSet = new TreeSet();

        treeSet.add(1);
        treeSet.add("2");
        treeSet.add(new ArrayList<Long>());

        log.info("size: ", treeSet.size());
        treeSet.stream().forEach(elm -> log.info(String.valueOf(elm)));
    }

    @Test
    public void testTreeSetWithGeneric() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("1");
        treeSet.add("2");
        // String 이외의 것을 넣으려고 하면 컴파일 에러 발생..

        log.info("size: ", treeSet.size());
        treeSet.stream().forEach(elm -> log.info(String.valueOf(elm)));
    }

}
