package com.rhyno.ch3;

import com.rhyno.rx.ch3.ConcatMap;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.junit.Test;

import java.time.DayOfWeek;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ConcatMapTest {
    @Test
    public void whenFlatMap_thenNotGuaranteeEventOrder() throws InterruptedException {
        TestObserver<String> testObserver = new TestObserver<>();
        Observable<String> observable = Observable
                .just(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY)
                .flatMap(dow -> ConcatMap.loadRecordsFor(dow));
        observable.subscribe(testObserver);

        TimeUnit.SECONDS.sleep(10);

        testObserver.assertComplete();

        List<Object> events = testObserver.getEvents().get(0);
        System.out.println("all emitted events" + events);

        List<String> firstTakenEvent = events.subList(0, 5)
                .stream()
                .map(e -> e.toString().split("-")[0])
                .filter(record -> "Sun".equals(record))
                .collect(Collectors.toList());
        System.out.println("flatMap does not guarantee event order!=" + firstTakenEvent);
        assertThat(firstTakenEvent.size()).isNotEqualTo(5);
    }

    @Test
    public void whenConcatMap_thenGuaranteeEventOrder() throws InterruptedException {
        TestObserver<String> testObserver = new TestObserver<>();
        Observable<String> observable = Observable
                .just(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY)
                .concatMap(dow -> ConcatMap.loadRecordsFor(dow));
        observable.subscribe(testObserver);

        TimeUnit.SECONDS.sleep(10);

        testObserver.assertComplete();

        List<Object> events = testObserver.getEvents().get(0);
        System.out.println("all emitted events" + events);

        List<String> firstTakenEvent = events.subList(0, 5)
                .stream()
                .map(e -> e.toString().split("-")[0])
                .filter(record -> "Sun".equals(record))
                .collect(Collectors.toList());
        System.out.println("concatMap guarantee event order!=" + firstTakenEvent);
        assertThat(firstTakenEvent.size()).isEqualTo(5);
    }
}
