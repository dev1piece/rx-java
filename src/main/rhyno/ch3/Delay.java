package com.rhyno.rx.ch3;

import io.reactivex.Observable;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static io.reactivex.Observable.just;
import static io.reactivex.Observable.timer;

public class Delay {
    public static void main(String[] args) throws InterruptedException {
        /**
         * http://reactivex.io/documentation/ko/operators  Observable 연산자 결정트리 참조
         *
         * delay vs timer vs interval
         * delay: Observable이 항목을 배출하기 전에 항목의 배출 시간을 지연
         * interval: 특정 시간 간격별로 항목을 배출해야 한다면
         * timer: 지정된 시간 이후에 항목을 배출해야 한다면
         */

        //delay는 timer+flatMap 사용하여 유사하게 변경이 가능하다.
        Delay subject = new Delay();
        subject.delay();

        subject.timerAndFlatMap();
    }

    private static void log(Integer i) {
        System.out.println("[" + Thread.currentThread().getName() + "] emit:" + i);
    }

    public Duration delay() throws InterruptedException {
        AtomicReference<Duration> diff = new AtomicReference<>();
        Instant start = Instant.now();
        Observable.just(1, 2, 3)
//                .delay(1, TimeUnit.SECONDS)
                .delay(i -> timer(1, TimeUnit.SECONDS))
                .subscribe(
                        i -> Delay.log(i),
                        e -> Observable.error(e),
                        () -> {
                            Instant completedTime = Instant.now();
                            diff.set(Duration.between(start, completedTime));
                        }
                );

//        Thread.sleep(2000);
        TimeUnit.SECONDS.sleep(2);

        System.out.println("[" + Thread.currentThread().getName() + "]");
        return diff.get();
    }

    public Duration timerAndFlatMap() throws InterruptedException {
        AtomicReference<Duration> diff = new AtomicReference<>();
        Instant start = Instant.now();
        timer(1, TimeUnit.SECONDS)
                .flatMap(i -> just(1, 2, 3))
                .subscribe(
                        i -> Delay.log(i),
                        e -> Observable.error(e),
                        () -> {
                            Instant completedTime = Instant.now();
                            diff.set(Duration.between(start, completedTime));
                        }
                );
//        Thread.sleep(2000);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("[" + Thread.currentThread().getName() + "]");
        return diff.get();
    }
}
