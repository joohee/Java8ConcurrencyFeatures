package net.joey.concurrent.function;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created with Java8ConcurrencyFeatures.
 * User: neigie
 * Date: 2016. 8. 27.
 * Time: 22:57
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class PredicateExampleMain {

    @Data
    public static class Player {

        private int age;
        private String name;

    }

    public static void main(String[] args) {

        List<Player> players = IntStream.range(1, 10).mapToObj(i -> {
            Player player = new Player();
            player.setAge(RandomUtils.nextInt(19, 50));
            player.setName(RandomStringUtils.randomAlphabetic(5));
            return player;
        }).collect(Collectors.toList());


        Predicate<Player> over40Predicate = player -> player.getAge() >= 40;
        Predicate<Player> abcPredicate = player -> StringUtils.startsWithAny(player.getName(), "a", "b", "c");



//        System.out.println(players);

        players.forEach(player1 -> log.info(String.valueOf(player1)));

        //
        players.stream().filter(abcPredicate).forEach(System.out::println);


        players.stream().filter(over40Predicate).forEach(System.out::println);


        System.out.println("The result");
        players.stream().filter(abcPredicate.and(over40Predicate)).forEach(System.out::println);





    }
}
