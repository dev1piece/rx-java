package com.rhyno.ch3;

import com.rhyno.rx.ch3.Merge;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MergeTest {
    private Merge subject;

    @Before
    public void setUp() {
        subject = new Merge();
    }

    @Test
    public void whenMergeWithObservables_then() throws InterruptedException {
        Observable<String> all = Observable.merge(
                subject.preciseAlgo(),
                subject.exprementalAlgo(),
                subject.fastAlgo()
        );

        TestObserver<String> testObserver = new TestObserver<>();
        all.subscribe(testObserver);

        TimeUnit.SECONDS.sleep(8);

        testObserver.assertComplete();

        List<String> emittedEvents = testObserver.getEvents().get(0)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        System.out.println("merge is not guarantee event order");
        System.out.println("[emitted events] " + emittedEvents);

        assertThat(emittedEvents).isNotEqualTo(Lists.newArrayList(
                "precise-1", "precise-2", "precise-3",
                "exp-1", "exp-2", "exp-3",
                "fast-1", "fast-2", "fast-3"
        ));
    }

    @Test
    public void whenMergeDuringThrowError_thenStopEmitEvents() throws InterruptedException {
        // Error Handling에는 여러가지 방법이 존재
        // 1. Action on Error
        AtomicBoolean isFail = new AtomicBoolean(false);

        Observable.merge(
                subject.preciseAlgo(),
                subject.exprementalAlgo(),
                subject.fastAlgo(),
                subject.throwErrorAlgo()
        )
        .subscribe(
                System.out::println,
                err -> {
                    System.out.println(Thread.currentThread().getName() + err);
                    isFail.set(true);
                });

        TimeUnit.SECONDS.sleep(8);

        assertThat(isFail).isTrue();

//        TestObserver<String> testObserver = new TestObserver<>();
//        all.subscribe(testObserver);
//
//        TimeUnit.SECONDS.sleep(8);
//
//        testObserver.assertError(err -> "fail to emit event: 2".equals(err.getMessage()));
//        List<String> emittedEvents = testObserver.getEvents().get(0)
//                .stream()
//                .map(Object::toString)
//                .collect(Collectors.toList());
//        System.out.println("[emitted events] " + emittedEvents);
//
//        assertThat(emittedEvents.size()).isNotEqualTo(9);
    }

    @Test
    public void whenMergeDelayError_thenDelayErrorAfterEmitAllEvents() throws InterruptedException {
        Observable<String> all = Observable.mergeDelayError(
                subject.preciseAlgo(),
                subject.exprementalAlgo(),
                subject.fastAlgo(),
                subject.throwErrorAlgo()
        );

        TestObserver<String> testObserver = new TestObserver<>();
        all.subscribe(testObserver);

        TimeUnit.SECONDS.sleep(8);

        testObserver.assertError(err -> "fail to emit event: 2".equals(err.getMessage()));
        List<String> emittedEvents = testObserver.getEvents().get(0)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        System.out.println("[emitted events] " + emittedEvents);

        assertThat(emittedEvents).contains(
                "precise-1", "precise-2", "precise-3",
                "exp-1", "exp-2", "exp-3",
                "fast-1", "fast-2", "fast-3"
        );
    }
}
