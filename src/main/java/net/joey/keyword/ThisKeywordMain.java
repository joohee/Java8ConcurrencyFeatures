package net.joey.keyword;

import lombok.extern.slf4j.Slf4j;

/**
 * Anonymous class 와 lambda expression 내부에서의 this keyword 차이점에 대해 보여주는 예제입니다.
 * <p>
 * Created by Joey on 2016. 8. 18..
 */
@Slf4j
public class ThisKeywordMain {

    public static void main(String[] args) {

        ThisKeywordMain thisKeywordMain = new ThisKeywordMain();

        thisKeywordMain.withAnonymousClass();
        thisKeywordMain.withLambda();
    }

    public void withLambda() {
        Thread t = new Thread(() -> {
            log.info("What is `this` in a lambda expression? \t{}", this);
            log.info("What is the class name of `this` in a lambda expression? \t{}", this.getClass().getName());
        });
        t.start();
    }

    public void withAnonymousClass() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("What is `this` in an anonymous class? \t{}", this);
                log.info("What is the class name `this` in an anonymous class? \t{}", this.getClass().getName());
            }
        });
        t.start();
    }

}
